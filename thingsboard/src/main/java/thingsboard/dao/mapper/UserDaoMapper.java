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
import thingsboard.common.data.User;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.page.TextPageLink;
import thingsboard.dao.Dao;

import java.util.List;
import java.util.UUID;

public interface UserDaoMapper extends Dao<User> {

    /**
     * Save or update user object
     *
     * @param user the user object
     * @return saved user entity
     */
    User save(@Param("tenantId")TenantId tenantId, @Param("user")User user);

    /**
     * Find user by email.
     *
     * @param email the email
     * @return the user entity
     */
    User findByEmail(@Param("tenantId")TenantId tenantId, @Param("email")String email);
    
    /**
     * Find tenant admin users by tenantId and page link.
     *
     * @param tenantId the tenantId
     * @param pageLink the page link
     * @return the list of user entities
     */
    List<User> findTenantAdmins(@Param("tenantId")UUID tenantId, @Param("pageLink")TextPageLink pageLink);
    
    /**
     * Find customer users by tenantId, customerId and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param pageLink the page link
     * @return the list of user entities
     */
    List<User> findCustomerUsers(@Param("tenantId")UUID tenantId, @Param("customerId")UUID customerId, @Param("pageLink")TextPageLink pageLink);
    
}

