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
package thingsboard.dao.sql.widget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import thingsboard.common.data.UUIDConverter;
import thingsboard.common.data.widget.WidgetType;
import thingsboard.dao.DaoUtil;
import thingsboard.dao.model.sql.WidgetTypeEntity;
import thingsboard.dao.sql.JpaAbstractDao;
import thingsboard.dao.util.SqlDao;
import thingsboard.dao.widget.WidgetTypeDao;

import java.util.List;
import java.util.UUID;

/**
 * Created by Valerii Sosliuk on 4/29/2017.
 */
@Component
//@SqlDao
public class JpaWidgetTypeDao extends JpaAbstractDao<WidgetTypeEntity, WidgetType> implements WidgetTypeDao {

    @Autowired
    private WidgetTypeRepository widgetTypeRepository;

    @Override
    protected Class<WidgetTypeEntity> getEntityClass() {
        return WidgetTypeEntity.class;
    }

    @Override
    protected CrudRepository<WidgetTypeEntity, String> getCrudRepository() {
        return widgetTypeRepository;
    }

    @Override
    public List<WidgetType> findWidgetTypesByTenantIdAndBundleAlias(UUID tenantId, String bundleAlias) {
        return DaoUtil.convertDataList(widgetTypeRepository.findByTenantIdAndBundleAlias(UUIDConverter.fromTimeUUID(tenantId), bundleAlias));
    }

    @Override
    public WidgetType findByTenantIdBundleAliasAndAlias(UUID tenantId, String bundleAlias, String alias) {
        return DaoUtil.getData(widgetTypeRepository.findByTenantIdAndBundleAliasAndAlias(UUIDConverter.fromTimeUUID(tenantId), bundleAlias, alias));
    }
}
