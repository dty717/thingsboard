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
import thingsboard.common.data.Device;
import thingsboard.common.data.EntitySubtype;
import thingsboard.common.data.EntityView;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.page.TextPageLink;
import thingsboard.dao.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Victor Basanets on 8/28/2017.
 */
public interface EntityViewDaoMapper extends Dao<EntityView> {

    /**
     * Save or update device object
     *
     * @param entityView the entity-view object
     * @return saved entity-view object
     */
    EntityView save(@Param("tenantId")TenantId tenantId, @Param("entityView")EntityView entityView);

    /**
     * Find entity views by tenantId and page link.
     *
     * @param tenantId the tenantId
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantId(@Param("tenantId")UUID tenantId, @Param("pageLink")TextPageLink pageLink);

    /**
     * Find entity views by tenantId, type and page link.
     *
     * @param tenantId the tenantId
     * @param type the type
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantIdAndType(@Param("tenantId")UUID tenantId, @Param("type")String type, @Param("pageLink")TextPageLink pageLink);

    /**
     * Find entity views by tenantId and entity view name.
     *
     * @param tenantId the tenantId
     * @param name the entity view name
     * @return the optional entity view object
     */
    Optional<EntityView> findEntityViewByTenantIdAndName(@Param("tenantId")UUID tenantId, @Param("name")String name);

    /**
     * Find entity views by tenantId, customerId and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantIdAndCustomerId(UUID tenantId,
                                                            UUID customerId,
                                                            TextPageLink pageLink);

    /**
     * Find entity views by tenantId, customerId, type and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param type the type
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantIdAndCustomerIdAndType(UUID tenantId,
                                                                   UUID customerId,
                                                                   String type,
                                                                   TextPageLink pageLink);

    ListenableFuture<List<EntityView>> findEntityViewsByTenantIdAndEntityIdAsync(@Param("tenantId")UUID tenantId, @Param("entityId")UUID entityId);

    /**
     * Find tenants entity view types.
     *
     * @return the list of tenant entity view type objects
     */
    ListenableFuture<List<EntitySubtype>> findTenantEntityViewTypesAsync(@Param("tenantId")UUID tenantId);

}


