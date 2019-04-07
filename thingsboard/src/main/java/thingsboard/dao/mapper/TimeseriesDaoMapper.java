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
import thingsboard.common.data.kv.DeleteTsKvQuery;
import thingsboard.common.data.kv.ReadTsKvQuery;
import thingsboard.common.data.kv.TsKvEntry;

import java.util.List;

/**
 * @author Andrew Shvayka
 */
public interface TimeseriesDaoMapper {

    ListenableFuture<List<TsKvEntry>> findAllAsync(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("queries")List<ReadTsKvQuery> queries);

    ListenableFuture<TsKvEntry> findLatest(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("key")String key);

    ListenableFuture<List<TsKvEntry>> findAllLatest(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId);

    ListenableFuture<Void> save(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("tsKvEntry")TsKvEntry tsKvEntry, @Param("ttl")long ttl);

    ListenableFuture<Void> savePartition(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("tsKvEntryTs")long tsKvEntryTs, @Param("key")String key, @Param("ttl")long ttl);

    ListenableFuture<Void> saveLatest(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("tsKvEntry")TsKvEntry tsKvEntry);

    ListenableFuture<Void> remove(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("query")DeleteTsKvQuery query);

    ListenableFuture<Void> removeLatest(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("query")DeleteTsKvQuery query);

    ListenableFuture<Void> removePartition(@Param("tenantId")TenantId tenantId, @Param("entityId")EntityId entityId, @Param("query")DeleteTsKvQuery query);
}

