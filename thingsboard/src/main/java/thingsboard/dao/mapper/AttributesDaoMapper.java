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
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.kv.AttributeKvEntry;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Shvayka
 */
public interface AttributesDaoMapper {

    ListenableFuture<Optional<AttributeKvEntry>> find(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("attributeType")String attributeType, @Param("attributeKey")String attributeKey);

    ListenableFuture<List<AttributeKvEntry>> find(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("attributeType")String attributeType, @Param("attributeKey")Collection<String> attributeKey);

    ListenableFuture<List<AttributeKvEntry>> findAll(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("attributeType")String attributeType);

    ListenableFuture<Void> save(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("attributeType")String attributeType, @Param("attribute")AttributeKvEntry attribute);

    ListenableFuture<List<Void>> removeAll(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("attributeType")String attributeType, @Param("keys")List<String> keys);
}


