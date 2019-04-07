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
package thingsboard.dao.component;

import com.fasterxml.jackson.databind.JsonNode;
import thingsboard.common.data.id.ComponentDescriptorId;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.page.TextPageData;
import thingsboard.common.data.page.TextPageLink;
import thingsboard.common.data.plugin.ComponentDescriptor;
import thingsboard.common.data.plugin.ComponentScope;
import thingsboard.common.data.plugin.ComponentType;

/**
 * @author Andrew Shvayka
 */
public interface ComponentDescriptorService {

    ComponentDescriptor saveComponent(TenantId tenantId, ComponentDescriptor component);

    ComponentDescriptor findById(TenantId tenantId, ComponentDescriptorId componentId);

    ComponentDescriptor findByClazz(TenantId tenantId, String clazz);

    TextPageData<ComponentDescriptor> findByTypeAndPageLink(TenantId tenantId, ComponentType type, TextPageLink pageLink);

    TextPageData<ComponentDescriptor> findByScopeAndTypeAndPageLink(TenantId tenantId, ComponentScope scope, ComponentType type, TextPageLink pageLink);

    boolean validate(TenantId tenantId, ComponentDescriptor component, JsonNode configuration);

    void deleteByClazz(TenantId tenantId, String clazz);

}