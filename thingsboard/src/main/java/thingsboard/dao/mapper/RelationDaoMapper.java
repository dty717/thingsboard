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
public interface RelationDaoMapper {

    ListenableFuture<List<EntityRelation>> findAllByFrom(@Param("tenantId")TenantId tenantId, @Param("from")EntityId from, @Param("typeGroup")RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByFromAndType(@Param("tenantId")TenantId tenantId, @Param("from")EntityId from, @Param("relationType")String relationType, @Param("typeGroup")RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByTo(@Param("tenantId")TenantId tenantId, @Param("to")EntityId to, @Param("typeGroup")RelationTypeGroup typeGroup);

    ListenableFuture<List<EntityRelation>> findAllByToAndType(@Param("tenantId")TenantId tenantId, @Param("to")EntityId to, @Param("relationType")String relationType, @Param("typeGroup")RelationTypeGroup typeGroup);

    ListenableFuture<Boolean> checkRelation(@Param("tenantId")TenantId tenantId, @Param("from")EntityId from, @Param("to")EntityId to, @Param("relationType")String relationType, @Param("typeGroup")RelationTypeGroup typeGroup);

    ListenableFuture<EntityRelation> getRelation(@Param("tenantId")TenantId tenantId, @Param("from")EntityId from, @Param("to")EntityId to, @Param("relationType")String relationType, @Param("typeGroup")RelationTypeGroup typeGroup);

    boolean saveRelation(@Param("tenantId")TenantId tenantId, @Param("relation")EntityRelation relation);

    ListenableFuture<Boolean> saveRelationAsync(@Param("tenantId")TenantId tenantId, @Param("relation")EntityRelation relation);

    boolean deleteRelation(@Param("tenantId")TenantId tenantId, @Param("relation")EntityRelation relation);

    ListenableFuture<Boolean> deleteRelationAsync(@Param("tenantId")TenantId tenantId, @Param("relation")EntityRelation relation);

    boolean deleteRelation(@Param("tenantId")TenantId tenantId, @Param("from")EntityId from, @Param("to")EntityId to, @Param("relationType")String relationType, @Param("typeGroup")RelationTypeGroup typeGroup);

    ListenableFuture<Boolean> deleteRelationAsync(@Param("tenantId")TenantId tenantId, @Param("from")EntityId from, @Param("to")EntityId to, @Param("relationType")String relationType, @Param("typeGroup")RelationTypeGroup typeGroup);

    boolean deleteOutboundRelations(@Param("tenantId")TenantId tenantId, @Param("entity")EntityId entity);

    ListenableFuture<Boolean> deleteOutboundRelationsAsync(@Param("tenantId")TenantId tenantId, @Param("entity")EntityId entity);

    ListenableFuture<List<EntityRelation>> findRelations(@Param("tenantId")TenantId tenantId, @Param("from")EntityId from, @Param("relationType")String relationType, @Param("typeGroup")RelationTypeGroup typeGroup, @Param("toType")EntityType toType, @Param("pageLink")TimePageLink pageLink);

}

