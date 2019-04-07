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
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thingsboard.common.data.id.ComponentDescriptorId;
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.page.TextPageData;
import thingsboard.common.data.page.TextPageLink;
import thingsboard.common.data.plugin.ComponentDescriptor;
import thingsboard.common.data.plugin.ComponentScope;
import thingsboard.common.data.plugin.ComponentType;
import thingsboard.dao.exception.DataValidationException;
import thingsboard.dao.exception.IncorrectParameterException;
import thingsboard.dao.service.DataValidator;
import thingsboard.dao.service.Validator;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Shvayka
 */
@Service
@Slf4j
public class BaseComponentDescriptorService implements ComponentDescriptorService {

    @Autowired
    private ComponentDescriptorDao componentDescriptorDao;

    @Override
    public ComponentDescriptor saveComponent(TenantId tenantId, ComponentDescriptor component) {
        componentValidator.validate(component, data -> new TenantId(EntityId.NULL_UUID));
        Optional<ComponentDescriptor> result = componentDescriptorDao.saveIfNotExist(tenantId, component);
        if (result.isPresent()) {
            return result.get();
        } else {
            return componentDescriptorDao.findByClazz(tenantId, component.getClazz());
        }
    }

    @Override
    public ComponentDescriptor findById(TenantId tenantId, ComponentDescriptorId componentId) {
        Validator.validateId(componentId, "Incorrect component id for search request.");
        return componentDescriptorDao.findById(tenantId, componentId);
    }

    @Override
    public ComponentDescriptor findByClazz(TenantId tenantId, String clazz) {
        Validator.validateString(clazz, "Incorrect clazz for search request.");
        return componentDescriptorDao.findByClazz(tenantId, clazz);
    }

    @Override
    public TextPageData<ComponentDescriptor> findByTypeAndPageLink(TenantId tenantId, ComponentType type, TextPageLink pageLink) {
        Validator.validatePageLink(pageLink, "Incorrect PageLink object for search plugin components request.");
        List<ComponentDescriptor> components = componentDescriptorDao.findByTypeAndPageLink(tenantId, type, pageLink);
        return new TextPageData<>(components, pageLink);
    }

    @Override
    public TextPageData<ComponentDescriptor> findByScopeAndTypeAndPageLink(TenantId tenantId, ComponentScope scope, ComponentType type, TextPageLink pageLink) {
        Validator.validatePageLink(pageLink, "Incorrect PageLink object for search plugin components request.");
        List<ComponentDescriptor> components = componentDescriptorDao.findByScopeAndTypeAndPageLink(tenantId, scope, type, pageLink);
        return new TextPageData<>(components, pageLink);
    }

    @Override
    public void deleteByClazz(TenantId tenantId, String clazz) {
        Validator.validateString(clazz, "Incorrect clazz for delete request.");
        componentDescriptorDao.deleteByClazz(tenantId, clazz);
    }

    @Override
    public boolean validate(TenantId tenantId, ComponentDescriptor component, JsonNode configuration) {
        JsonValidator validator = JsonSchemaFactory.byDefault().getValidator();
        try {
            if (!component.getConfigurationDescriptor().has("schema")) {
                throw new DataValidationException("Configuration descriptor doesn't contain schema property!");
            }
            JsonNode configurationSchema = component.getConfigurationDescriptor().get("schema");
            ProcessingReport report = validator.validate(configurationSchema, configuration);
            return report.isSuccess();
        } catch (ProcessingException e) {
            throw new IncorrectParameterException(e.getMessage(), e);
        }
    }

    private DataValidator<ComponentDescriptor> componentValidator =
            new DataValidator<ComponentDescriptor>() {
                @Override
                protected void validateDataImpl(TenantId tenantId, ComponentDescriptor plugin) {
                    if (plugin.getType() == null) {
                        throw new DataValidationException("Component type should be specified!.");
                    }
                    if (plugin.getScope() == null) {
                        throw new DataValidationException("Component scope should be specified!.");
                    }
                    if (StringUtils.isEmpty(plugin.getName())) {
                        throw new DataValidationException("Component name should be specified!.");
                    }
                    if (StringUtils.isEmpty(plugin.getClazz())) {
                        throw new DataValidationException("Component clazz should be specified!.");
                    }
                }
            };
}
