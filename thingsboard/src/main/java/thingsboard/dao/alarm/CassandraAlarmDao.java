/**
 * Copyright © 2016-2019 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package thingsboard.dao.alarm;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thingsboard.common.data.EntityType;
import thingsboard.common.data.alarm.Alarm;
import thingsboard.common.data.alarm.AlarmInfo;
import thingsboard.common.data.alarm.AlarmQuery;
import thingsboard.common.data.alarm.AlarmSearchStatus;
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.alarm.AlarmId;
import thingsboard.common.data.relation.EntityRelation;
import thingsboard.common.data.relation.RelationTypeGroup;
import thingsboard.dao.model.ModelConstants;
import thingsboard.dao.model.nosql.AlarmEntity;
import thingsboard.dao.nosql.CassandraAbstractModelDao;
import thingsboard.dao.relation.RelationDao;
import thingsboard.dao.util.NoSqlDao;
import thingsboard.dao.mapper.AlarmDaoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static thingsboard.dao.model.ModelConstants.ALARM_BY_ID_VIEW_NAME;
import static thingsboard.dao.model.ModelConstants.ALARM_COLUMN_FAMILY_NAME;
import static thingsboard.dao.model.ModelConstants.ALARM_ORIGINATOR_ID_PROPERTY;
import static thingsboard.dao.model.ModelConstants.ALARM_ORIGINATOR_TYPE_PROPERTY;
import static thingsboard.dao.model.ModelConstants.ALARM_TENANT_ID_PROPERTY;
import static thingsboard.dao.model.ModelConstants.ALARM_TYPE_PROPERTY;

import java.util.UUID;

@Component
@Slf4j
//@NoSqlDao
public class CassandraAlarmDao extends CassandraAbstractModelDao<AlarmEntity, Alarm> implements AlarmDao {
    @Autowired
    private AlarmDaoMapper alarmDaoMapper;
    
    static EntityId _originator=new AlarmId(UUID.randomUUID());
    public String test(){
        
        System.out.println(_originator.getId()+", "+_originator.getEntityType());
        return alarmDaoMapper.findLatestByOriginatorAndType(null,_originator,"0").toString();
        // return alarmDaoMapper.test();
    }
    @Autowired
    private RelationDao relationDao;

    @Override
    protected Class<AlarmEntity> getColumnFamilyClass() {
        return AlarmEntity.class;
    }

    @Override
    protected String getColumnFamilyName() {
        return ALARM_COLUMN_FAMILY_NAME;
    }

    protected boolean isDeleteOnSave() {
        return false;
    }

    @Override
    public Alarm save(TenantId tenantId, Alarm alarm) {
        log.debug("Save asset [{}] ", alarm);
        return super.save(tenantId, alarm);
    }

    @Override
    public Boolean deleteAlarm(TenantId tenantId, Alarm alarm) {
        Statement delete = QueryBuilder.delete().all().from(getColumnFamilyName()).where(eq(ModelConstants.ID_PROPERTY, alarm.getId().getId()))
            .and(eq(ALARM_TENANT_ID_PROPERTY, tenantId.getId()))
            .and(eq(ALARM_ORIGINATOR_ID_PROPERTY, alarm.getOriginator().getId()))
            .and(eq(ALARM_ORIGINATOR_TYPE_PROPERTY, alarm.getOriginator().getEntityType()))
            .and(eq(ALARM_TYPE_PROPERTY, alarm.getType()));
        log.debug("Remove request: {}", delete.toString());
        return executeWrite(tenantId, delete).wasApplied();
    }

    @Override
    public ListenableFuture<Alarm> findLatestByOriginatorAndType(TenantId tenantId, EntityId originator, String type) {
        // return alarmDaoMapper.findLatestByOriginatorAndType(tenantId, originator, type);
        Select select = select().from(ALARM_COLUMN_FAMILY_NAME);
        Select.Where query = select.where();
        query.and(eq(ALARM_TENANT_ID_PROPERTY, tenantId.getId()));
        query.and(eq(ALARM_ORIGINATOR_ID_PROPERTY, originator.getId()));
        query.and(eq(ALARM_ORIGINATOR_TYPE_PROPERTY, originator.getEntityType()));
        query.and(eq(ALARM_TYPE_PROPERTY, type));
        query.limit(1);
        query.orderBy(QueryBuilder.asc(ModelConstants.ALARM_TYPE_PROPERTY), QueryBuilder.desc(ModelConstants.ID_PROPERTY));
        return findOneByStatementAsync(tenantId, query);
    }

    @Override
    public ListenableFuture<List<AlarmInfo>> findAlarms(TenantId tenantId, AlarmQuery query) {
        log.trace("Try to find alarms by entity [{}], searchStatus [{}], status [{}] and pageLink [{}]", query.getAffectedEntityId(), query.getSearchStatus(), query.getStatus(), query.getPageLink());
        EntityId affectedEntity = query.getAffectedEntityId();
        String searchStatusName;
        if (query.getSearchStatus() == null && query.getStatus() == null) {
            searchStatusName = AlarmSearchStatus.ANY.name();
        } else if (query.getSearchStatus() != null) {
            searchStatusName = query.getSearchStatus().name();
        } else {
            searchStatusName = query.getStatus().name();
        }
        String relationType = BaseAlarmService.ALARM_RELATION_PREFIX + searchStatusName;
        ListenableFuture<List<EntityRelation>> relations = relationDao.findRelations(tenantId, affectedEntity, relationType, RelationTypeGroup.ALARM, EntityType.ALARM, query.getPageLink());
        return Futures.transformAsync(relations, input -> {
            List<ListenableFuture<AlarmInfo>> alarmFutures = new ArrayList<>(input.size());
            for (EntityRelation relation : input) {
                alarmFutures.add(Futures.transform(
                        findAlarmByIdAsync(tenantId, relation.getTo().getId()),
                        AlarmInfo::new));
            }
            return Futures.successfulAsList(alarmFutures);
        });
    }

    @Override
    public ListenableFuture<Alarm> findAlarmByIdAsync(TenantId tenantId, UUID key) {
        log.debug("Get alarm by id {}", key);
        Select.Where query = select().from(ALARM_BY_ID_VIEW_NAME).where(eq(ModelConstants.ID_PROPERTY, key));
        query.limit(1);
        log.trace("Execute query {}", query);
        return findOneByStatementAsync(tenantId, query);
    }
}
