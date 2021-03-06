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
package thingsboard.common.data.device;

import lombok.Data;
import thingsboard.common.data.EntityType;
import thingsboard.common.data.relation.EntityRelation;
import thingsboard.common.data.relation.EntityRelationsQuery;
import thingsboard.common.data.relation.EntityTypeFilter;
import thingsboard.common.data.relation.RelationsSearchParameters;

import java.util.Collections;
import java.util.List;

@Data
public class DeviceSearchQuery {

    private RelationsSearchParameters parameters;
    private String relationType;
    private List<String> deviceTypes;

    public EntityRelationsQuery toEntitySearchQuery() {
        EntityRelationsQuery query = new EntityRelationsQuery();
        query.setParameters(parameters);
        query.setFilters(
                Collections.singletonList(new EntityTypeFilter(relationType == null ? EntityRelation.CONTAINS_TYPE : relationType,
                        Collections.singletonList(EntityType.DEVICE))));
        return query;
    }
}
