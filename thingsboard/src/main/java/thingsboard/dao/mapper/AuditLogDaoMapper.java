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
import thingsboard.common.data.audit.AuditLog;
import thingsboard.common.data.id.CustomerId;
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.UserId;
import thingsboard.common.data.page.TimePageLink;

import java.util.List;
import java.util.UUID;

public interface AuditLogDaoMapper {

    ListenableFuture<Void> saveByTenantId(@Param("auditLog")AuditLog auditLog);

    ListenableFuture<Void> saveByTenantIdAndEntityId(@Param("auditLog")AuditLog auditLog);

    ListenableFuture<Void> saveByTenantIdAndCustomerId(@Param("auditLog")AuditLog auditLog);

    ListenableFuture<Void> saveByTenantIdAndUserId(@Param("auditLog")AuditLog auditLog);

    ListenableFuture<Void> savePartitionsByTenantId(@Param("auditLog")AuditLog auditLog);

    List<AuditLog> findAuditLogsByTenantIdAndEntityId(@Param("tenantId")UUID tenantId, @Param("entityId")EntityId entityId, @Param("pageLink")TimePageLink pageLink);

    List<AuditLog> findAuditLogsByTenantIdAndCustomerId(@Param("tenantId")UUID tenantId, @Param("customerId")CustomerId customerId, @Param("pageLink")TimePageLink pageLink);

    List<AuditLog> findAuditLogsByTenantIdAndUserId(@Param("tenantId")UUID tenantId, @Param("userId")UserId userId, @Param("pageLink")TimePageLink pageLink);

    List<AuditLog> findAuditLogsByTenantId(@Param("tenantId")UUID tenantId, @Param("pageLink")TimePageLink pageLink);
}

