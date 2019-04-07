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
import thingsboard.common.data.AdminSettings;
import thingsboard.common.data.id.TenantId;
import thingsboard.dao.Dao;

public interface AdminSettingsDaoMapper extends Dao<AdminSettings> {

    /**
     * Save or update admin settings object
     *
     * @param adminSettings the admin settings object
     * @return saved admin settings object
     */
    AdminSettings save(@Param("tenantId")TenantId tenantId, @Param("adminSettings")AdminSettings adminSettings);
    
    /**
     * Find admin settings by key.
     *
     * @param key the key
     * @return the admin settings object
     */
    AdminSettings findByKey(@Param("tenantId")TenantId tenantId, @Param("key")String key);

}

