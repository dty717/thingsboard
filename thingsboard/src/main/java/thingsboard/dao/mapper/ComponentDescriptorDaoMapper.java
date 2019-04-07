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
import thingsboard.common.data.id.ComponentDescriptorId;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.page.TextPageLink;
import thingsboard.common.data.plugin.ComponentDescriptor;
import thingsboard.common.data.plugin.ComponentScope;
import thingsboard.common.data.plugin.ComponentType;
import thingsboard.dao.Dao;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Shvayka
 */
public interface ComponentDescriptorDaoMapper extends Dao<ComponentDescriptor> {

    Optional<ComponentDescriptor> saveIfNotExist(@Param("tenantId")TenantId tenantId, @Param("component")ComponentDescriptor component);

    ComponentDescriptor findById(@Param("tenantId")TenantId tenantId, @Param("componentId")ComponentDescriptorId componentId);

    ComponentDescriptor findByClazz(@Param("tenantId")TenantId tenantId, @Param("clazz")String clazz);

    List<ComponentDescriptor> findByTypeAndPageLink(@Param("tenantId")TenantId tenantId, @Param("type")ComponentType type, @Param("pageLink")TextPageLink pageLink);

    List<ComponentDescriptor> findByScopeAndTypeAndPageLink(@Param("tenantId")TenantId tenantId, @Param("scope")ComponentScope scope, @Param("type")ComponentType type, @Param("pageLink")TextPageLink pageLink);

    void deleteById(@Param("tenantId")TenantId tenantId, @Param("componentId")ComponentDescriptorId componentId);

    void deleteByClazz(@Param("tenantId")TenantId tenantId, @Param("clazz")String clazz);

}


