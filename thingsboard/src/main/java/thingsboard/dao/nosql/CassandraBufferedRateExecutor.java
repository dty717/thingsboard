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
package thingsboard.dao.nosql;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.google.common.util.concurrent.SettableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import thingsboard.common.data.id.TenantId;
import thingsboard.dao.entity.EntityService;
import thingsboard.dao.util.AbstractBufferedRateExecutor;
import thingsboard.dao.util.AsyncTaskContext;
import thingsboard.dao.util.NoSqlAnyDao;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashvayka on 24.10.18.
 */
@Component
@Slf4j
//@NoSqlAnyDao
public class CassandraBufferedRateExecutor extends AbstractBufferedRateExecutor<CassandraStatementTask, ResultSetFuture, ResultSet> {

    @Autowired
    private EntityService entityService;
    private Map<TenantId, String> tenantNamesCache = new HashMap<>();

    private boolean printTenantNames;
    
    public CassandraBufferedRateExecutor(
            @Value("100000")/*@Value("${cassandra.query.buffer_size}")*/ int queueLimit,
            @Value("1000")/*@Value("${cassandra.query.concurrent_limit}")*/ int concurrencyLimit,
            @Value("20000")/*@Value("${cassandra.query.permit_max_wait_time}")*/ long maxWaitTime,
            @Value("2")/*@Value("${cassandra.query.dispatcher_threads:2}")*/ int dispatcherThreads,
            @Value("4")/*@Value("${cassandra.query.callback_threads:4}")*/ int callbackThreads,
            @Value("50")/*@Value("${cassandra.query.poll_ms:50}")*/ long pollMs,
            @Value("false")/*@Value("${cassandra.query.tenant_rate_limits.enabled}")*/ boolean tenantRateLimitsEnabled,
            @Value("5000:1,100000:60")/*@Value("${cassandra.query.tenant_rate_limits.configuration}")*/ String tenantRateLimitsConfiguration,
            @Value("false")/*@Value("${cassandra.query.tenant_rate_limits.print_tenant_names}")*/ boolean printTenantNames) {
        super(queueLimit, concurrencyLimit, maxWaitTime, dispatcherThreads, callbackThreads, pollMs, tenantRateLimitsEnabled, tenantRateLimitsConfiguration);
        this.printTenantNames = printTenantNames;
    }

    //@Scheduled(fixedDelayString = "${cassandra.query.rate_limit_print_interval_ms}")
    @Scheduled(fixedDelayString = "10000000")
    public void printStats() {
        log.info("Permits queueSize [{}] totalAdded [{}] totalLaunched [{}] totalReleased [{}] totalFailed [{}] totalExpired [{}] totalRejected [{}] " +
                        "totalRateLimited [{}] totalRateLimitedTenants [{}] currBuffer [{}] ",
                getQueueSize(),
                totalAdded.getAndSet(0), totalLaunched.getAndSet(0), totalReleased.getAndSet(0),
                totalFailed.getAndSet(0), totalExpired.getAndSet(0), totalRejected.getAndSet(0),
                totalRateLimited.getAndSet(0), rateLimitedTenants.size(), concurrencyLevel.get());

        rateLimitedTenants.forEach(((tenantId, counter) -> {
            if (printTenantNames) {
                String name = tenantNamesCache.computeIfAbsent(tenantId, tId -> {
                    try {
                        return entityService.fetchEntityNameAsync(TenantId.SYS_TENANT_ID, tenantId).get();
                    } catch (Exception e) {
                        log.error("[{}] Failed to get tenant name", tenantId, e);
                        return "N/A";
                    }
                });
                log.info("[{}][{}] Rate limited requests: {}", tenantId, name, counter);
            } else {
                log.info("[{}] Rate limited requests: {}", tenantId, counter);
            }
        }));
        rateLimitedTenants.clear();
    }

    @PreDestroy
    public void stop() {
        super.stop();
    }

    @Override
    protected SettableFuture<ResultSet> create() {
        return SettableFuture.create();
    }

    @Override
    protected ResultSetFuture wrap(CassandraStatementTask task, SettableFuture<ResultSet> future) {
        return new TbResultSetFuture(future);
    }

    @Override
    protected ResultSetFuture execute(AsyncTaskContext<CassandraStatementTask, ResultSet> taskCtx) {
        CassandraStatementTask task = taskCtx.getTask();
        return task.getSession().executeAsync(task.getStatement());
    }

}
