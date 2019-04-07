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
package thingsboard.dao.relation;

import com.google.common.util.concurrent.ListenableFuture;
import thingsboard.common.data.EntityType;
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.page.TimePageLink;
import thingsboard.common.data.relation.EntityRelation;
import thingsboard.common.data.relation.RelationTypeGroup;

import java.util.List;

/**
 * Created by ashvayka on 25.04.17.
 */
public interface RelationDao {

    ListenableFuture<List<EntityRelation>> findAllByFrom(TenantId tenantId, EntityId from, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByFromAndType(TenantId tenantId, EntityId from, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByTo(TenantId tenantId, EntityId to, RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByToAndType(TenantId tenantId, EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<Boolean> checkRelation(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<EntityRelation> getRelation(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    boolean saveRelation(TenantId tenantId, EntityRelation relation);

    ListenableFuture<Boolean> saveRelationAsync(TenantId tenantId, EntityRelation relation);

    boolean deleteRelation(TenantId tenantId, EntityRelation relation);

    ListenableFuture<Boolean> deleteRelationAsync(TenantId tenantId, EntityRelation relation);

    boolean deleteRelation(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    ListenableFuture<Boolean> deleteRelationAsync(TenantId tenantId, EntityId from, EntityId to, String relationType, RelationTypeGroup typeGroup);

    boolean deleteOutboundRelations(TenantId tenantId, EntityId entity);

    ListenableFuture<Boolean> deleteOutboundRelationsAsync(TenantId tenantId, EntityId entity);

    ListenableFuture<List<EntityRelation>> findRelations(TenantId tenantId, EntityId from, String relationType, RelationTypeGroup typeGroup, EntityType toType, TimePageLink pageLink);

}