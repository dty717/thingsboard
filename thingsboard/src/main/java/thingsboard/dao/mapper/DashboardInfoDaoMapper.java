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
import com.google.common.util.concurrent.ListenableFuture;
import thingsboard.common.data.DashboardInfo;
import thingsboard.common.data.page.TextPageLink;
import thingsboard.common.data.page.TimePageLink;
import thingsboard.dao.Dao;

import java.util.List;
import java.util.UUID;

/**
 * The Interface DashboardInfoDao.
 */
public interface DashboardInfoDaoMapper extends Dao<DashboardInfo> {

    /**
     * Find dashboards by tenantId and page link.
     *
     * @param tenantId the tenantId
     * @param pageLink the page link
     * @return the list of dashboard objects
     */
    List<DashboardInfo> findDashboardsByTenantId(@Param("tenantId")UUID tenantId, @Param("pageLink")TextPageLink pageLink);

    /**
     * Find dashboards by tenantId, customerId and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param pageLink the page link
     * @return the list of dashboard objects
     */
    ListenableFuture<List<DashboardInfo>> findDashboardsByTenantIdAndCustomerId(@Param("tenantId")UUID tenantId, @Param("customerId")UUID customerId, @Param("pageLink")TimePageLink pageLink);

}


