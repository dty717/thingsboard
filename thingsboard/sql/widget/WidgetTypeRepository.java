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
package thingsboard.dao.sql.widget;

import org.springframework.data.repository.CrudRepository;
import thingsboard.dao.model.sql.WidgetTypeEntity;
import thingsboard.dao.util.SqlDao;

import java.util.List;

/**
 * Created by Valerii Sosliuk on 4/29/2017.
 */
//@SqlDao
public interface WidgetTypeRepository extends CrudRepository<WidgetTypeEntity, String> {

    List<WidgetTypeEntity> findByTenantIdAndBundleAlias(String tenantId, String bundleAlias);

    WidgetTypeEntity findByTenantIdAndBundleAliasAndAlias(String tenantId, String bundleAlias, String alias);
}
