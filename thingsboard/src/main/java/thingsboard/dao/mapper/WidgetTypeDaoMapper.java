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
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.widget.WidgetType;
import thingsboard.dao.Dao;

import java.util.List;
import java.util.UUID;

/**
 * The Interface WidgetTypeDao.
 */
public interface WidgetTypeDaoMapper extends Dao<WidgetType> {

    /**
     * Save or update widget type object
     *
     * @param widgetType the widget type object
     * @return saved widget type object
     */
    WidgetType save(@Param("tenantId")TenantId tenantId, @Param("widgetType")WidgetType widgetType);

    /**
     * Find widget types by tenantId and bundleAlias.
     *
     * @param tenantId the tenantId
     * @param bundleAlias the bundle alias
     * @return the list of widget types objects
     */
    List<WidgetType> findWidgetTypesByTenantIdAndBundleAlias(@Param("tenantId")UUID tenantId, @Param("bundleAlias")String bundleAlias);

    /**
     * Find widget type by tenantId, bundleAlias and alias.
     *
     * @param tenantId the tenantId
     * @param bundleAlias the bundle alias
     * @param alias the alias
     * @return the widget type object
     */
    WidgetType findByTenantIdBundleAliasAndAlias(@Param("tenantId")UUID tenantId, @Param("bundleAlias")String bundleAlias, @Param("alias")String alias);

}

