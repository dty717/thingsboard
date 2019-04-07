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
package thingsboard.dao.mapper;

import org.apache.ibatis.annotations.Param;
import com.google.common.util.concurrent.ListenableFuture;
import thingsboard.common.data.alarm.Alarm;
import thingsboard.common.data.alarm.AlarmInfo;
import thingsboard.common.data.alarm.AlarmQuery;
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.TenantId;
import thingsboard.dao.Dao;

import java.util.List;
import java.util.UUID;

/**
 * Created by ashvayka on 11.05.17.
 */
public interface AlarmDaoMapper extends Dao<Alarm> {

    Boolean deleteAlarm(@Param("tenantId")TenantId tenantId, @Param("alarm")Alarm alarm);

    /*ListenableFuture<Alarm>*/Alarm findLatestByOriginatorAndType(@Param("tenantId")TenantId tenantId, @Param("originator")EntityId originator, @Param("type")String type);

    ListenableFuture<Alarm> findAlarmByIdAsync(@Param("tenantId")TenantId tenantId, @Param("key")UUID key);

    Alarm save(@Param("tenantId")TenantId tenantId, @Param("alarm")Alarm alarm);

    ListenableFuture<List<AlarmInfo>> findAlarms(@Param("tenantId")TenantId tenantId, @Param("query")AlarmQuery query);
    
    String test();
}
