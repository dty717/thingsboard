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
package thingsboard.common.data.alarm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import thingsboard.common.data.EntityType;
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.UUIDBased;

import java.util.UUID;

public class AlarmId extends UUIDBased implements EntityId {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public AlarmId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static AlarmId fromString(String alarmId) {
        return new AlarmId(UUID.fromString(alarmId));
    }

    @JsonIgnore
    @Override
    public EntityType getEntityType() {
        return EntityType.ALARM;
    }
}
