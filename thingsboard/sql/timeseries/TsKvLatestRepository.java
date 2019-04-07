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
package thingsboard.dao.sql.timeseries;

import org.springframework.data.repository.CrudRepository;
import thingsboard.common.data.EntityType;
import thingsboard.dao.model.sql.TsKvLatestCompositeKey;
import thingsboard.dao.model.sql.TsKvLatestEntity;
import thingsboard.dao.util.SqlDao;

import java.util.List;

//@SqlDao
public interface TsKvLatestRepository extends CrudRepository<TsKvLatestEntity, TsKvLatestCompositeKey> {

    List<TsKvLatestEntity> findAllByEntityTypeAndEntityId(EntityType entityType, String entityId);
}
