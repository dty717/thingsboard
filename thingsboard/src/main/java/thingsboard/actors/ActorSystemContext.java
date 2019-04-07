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
package thingsboard.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.thingsboard.rule.engine.api.MailService;
import org.thingsboard.rule.engine.api.RuleChainTransactionService;
/*import thingsboard.actors.service.ActorService;
import thingsboard.common.data.DataConstants;
import thingsboard.common.data.Event;
import thingsboard.common.data.id.EntityId;
import thingsboard.common.data.id.TenantId;
import thingsboard.common.data.plugin.ComponentLifecycleEvent;
import thingsboard.common.msg.TbMsg;
import thingsboard.common.msg.cluster.ServerAddress;
import thingsboard.common.transport.auth.DeviceAuthService;
import thingsboard.dao.alarm.AlarmService;
import thingsboard.dao.asset.AssetService;
import thingsboard.dao.attributes.AttributesService;
import thingsboard.dao.audit.AuditLogService;
import thingsboard.dao.customer.CustomerService;
import thingsboard.dao.dashboard.DashboardService;
import thingsboard.dao.device.DeviceService;
import thingsboard.dao.entityview.EntityViewService;
import thingsboard.dao.event.EventService;
import thingsboard.dao.relation.RelationService;
import thingsboard.dao.rule.RuleChainService;
import thingsboard.dao.tenant.TenantService;
import thingsboard.dao.timeseries.TimeseriesService;
import thingsboard.dao.user.UserService;
import thingsboard.kafka.TbNodeIdProvider;
import thingsboard.service.cluster.discovery.DiscoveryService;
import thingsboard.service.cluster.routing.ClusterRoutingService;
import thingsboard.service.cluster.rpc.ClusterRpcService;
import thingsboard.service.component.ComponentDiscoveryService;
import thingsboard.service.encoding.DataDecodingEncodingService;
import thingsboard.service.executors.ClusterRpcCallbackExecutorService;
import thingsboard.service.executors.DbCallbackExecutorService;
import thingsboard.service.executors.ExternalCallExecutorService;
import thingsboard.service.executors.SharedEventLoopGroupService;
import thingsboard.service.mail.MailExecutorService;
import thingsboard.service.rpc.DeviceRpcService;
import thingsboard.service.script.JsExecutorService;
import thingsboard.service.script.JsInvokeService;
import thingsboard.service.session.DeviceSessionCacheService;
import thingsboard.service.state.DeviceStateService;
import thingsboard.service.telemetry.TelemetrySubscriptionService;
import thingsboard.service.transport.RuleEngineTransportService;*/

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@Slf4j
@Component
public class ActorSystemContext {
    private static final String AKKA_CONF_FILE_NAME = "actor-system.conf";

    protected final ObjectMapper mapper = new ObjectMapper();
/*
    @Getter
    @Setter
    private ActorService actorService;

    @Autowired
    @Getter
    private DiscoveryService discoveryService;

    @Autowired
    @Getter
    @Setter
    private ComponentDiscoveryService componentService;

    @Autowired
    @Getter
    private ClusterRoutingService routingService;

    @Autowired
    @Getter
    private ClusterRpcService rpcService;

    @Autowired
    @Getter
    private DataDecodingEncodingService encodingService;

    @Autowired
    @Getter
    private DeviceAuthService deviceAuthService;

    @Autowired
    @Getter
    private DeviceService deviceService;

    @Autowired
    @Getter
    private AssetService assetService;

    @Autowired
    @Getter
    private DashboardService dashboardService;

    @Autowired
    @Getter
    private TenantService tenantService;

    @Autowired
    @Getter
    private CustomerService customerService;

    @Autowired
    @Getter
    private UserService userService;

    @Autowired
    @Getter
    private RuleChainService ruleChainService;

    @Autowired
    @Getter
    private TimeseriesService tsService;

    @Autowired
    @Getter
    private AttributesService attributesService;

    @Autowired
    @Getter
    private EventService eventService;

    @Autowired
    @Getter
    private AlarmService alarmService;

    @Autowired
    @Getter
    private RelationService relationService;

    @Autowired
    @Getter
    private AuditLogService auditLogService;

    @Autowired
    @Getter
    private EntityViewService entityViewService;

    @Autowired
    @Getter
    private TelemetrySubscriptionService tsSubService;

    @Autowired
    @Getter
    private DeviceRpcService deviceRpcService;

    @Autowired
    @Getter
    private JsInvokeService jsSandbox;

    @Autowired
    @Getter
    private JsExecutorService jsExecutor;

    @Autowired
    @Getter
    private MailExecutorService mailExecutor;

    @Autowired
    @Getter
    private ClusterRpcCallbackExecutorService clusterRpcCallbackExecutor;

    @Autowired
    @Getter
    private DbCallbackExecutorService dbCallbackExecutor;

    @Autowired
    @Getter
    private ExternalCallExecutorService externalCallExecutorService;

    @Autowired
    @Getter
    private SharedEventLoopGroupService sharedEventLoopGroupService;
*/
    /*@Autowired
    @Getter
    private MailService mailService;*/
/*
    @Autowired
    @Getter
    private DeviceStateService deviceStateService;

    @Autowired
    @Getter
    private DeviceSessionCacheService deviceSessionCacheService;

    @Lazy
    @Autowired
    @Getter
    private RuleEngineTransportService ruleEngineTransportService;

    @Lazy
    @Autowired
    @Getter
    private RuleChainTransactionService ruleChainTransactionService;

    @Value("${cluster.partition_id}")
    @Getter
    private long queuePartitionId;

    @Value("${actors.session.max_concurrent_sessions_per_device:1}")
    @Getter
    private long maxConcurrentSessionsPerDevice;

    @Value("${actors.session.sync.timeout}")
    @Getter
    private long syncSessionTimeout;

    @Value("${actors.queue.enabled}")
    @Getter
    private boolean queuePersistenceEnabled;

    @Value("${actors.queue.timeout}")
    @Getter
    private long queuePersistenceTimeout;

    @Value("${actors.client_side_rpc.timeout}")
    @Getter
    private long clientSideRpcTimeout;

    @Value("${actors.rule.chain.error_persist_frequency}")
    @Getter
    private long ruleChainErrorPersistFrequency;

    @Value("${actors.rule.node.error_persist_frequency}")
    @Getter
    private long ruleNodeErrorPersistFrequency;

    @Value("${actors.statistics.enabled}")
    @Getter
    private boolean statisticsEnabled;

    @Value("${actors.statistics.persist_frequency}")
    @Getter
    private long statisticsPersistFrequency;

    @Value("${actors.tenant.create_components_on_init}")
    @Getter
    private boolean tenantComponentsInitEnabled;

    @Value("${actors.rule.allow_system_mail_service}")
    @Getter
    private boolean allowSystemMailService;

    @Value("${transport.sessions.inactivity_timeout}")
    @Getter
    private long sessionInactivityTimeout;

    @Value("${transport.sessions.report_timeout}")
    @Getter
    private long sessionReportTimeout;

    @Getter
    @Setter
    private ActorSystem actorSystem;

    @Autowired
    @Getter
    private TbNodeIdProvider nodeIdProvider;

    @Getter
    @Setter
    private ActorRef appActor;

    @Getter
    @Setter
    private ActorRef statsActor;

    @Getter
    private final Config config;

    public ActorSystemContext() {
        config = ConfigFactory.parseResources(AKKA_CONF_FILE_NAME).withFallback(ConfigFactory.load());
    }

    public Scheduler getScheduler() {
        return actorSystem.scheduler();
    }

    public void persistError(TenantId tenantId, EntityId entityId, String method, Exception e) {
        Event event = new Event();
        event.setTenantId(tenantId);
        event.setEntityId(entityId);
        event.setType(DataConstants.ERROR);
        event.setBody(toBodyJson(discoveryService.getCurrentServer().getServerAddress(), method, toString(e)));
        persistEvent(event);
    }

    public void persistLifecycleEvent(TenantId tenantId, EntityId entityId, ComponentLifecycleEvent lcEvent, Exception e) {
        Event event = new Event();
        event.setTenantId(tenantId);
        event.setEntityId(entityId);
        event.setType(DataConstants.LC_EVENT);
        event.setBody(toBodyJson(discoveryService.getCurrentServer().getServerAddress(), lcEvent, Optional.ofNullable(e)));
        persistEvent(event);
    }

    private void persistEvent(Event event) {
        eventService.save(event);
    }

    private String toString(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private JsonNode toBodyJson(ServerAddress server, ComponentLifecycleEvent event, Optional<Exception> e) {
        ObjectNode node = mapper.createObjectNode().put("server", server.toString()).put("event", event.name());
        if (e.isPresent()) {
            node = node.put("success", false);
            node = node.put("error", toString(e.get()));
        } else {
            node = node.put("success", true);
        }
        return node;
    }

    private JsonNode toBodyJson(ServerAddress server, String method, String body) {
        return mapper.createObjectNode().put("server", server.toString()).put("method", method).put("error", body);
    }

    public String getServerAddress() {
        return discoveryService.getCurrentServer().getServerAddress().toString();
    }

    public void persistDebugInput(TenantId tenantId, EntityId entityId, TbMsg tbMsg, String relationType) {
        persistDebugAsync(tenantId, entityId, "IN", tbMsg, relationType, null);
    }

    public void persistDebugInput(TenantId tenantId, EntityId entityId, TbMsg tbMsg, String relationType, Throwable error) {
        persistDebugAsync(tenantId, entityId, "IN", tbMsg, relationType, error);
    }

    public void persistDebugOutput(TenantId tenantId, EntityId entityId, TbMsg tbMsg, String relationType, Throwable error) {
        persistDebugAsync(tenantId, entityId, "OUT", tbMsg, relationType, error);
    }

    public void persistDebugOutput(TenantId tenantId, EntityId entityId, TbMsg tbMsg, String relationType) {
        persistDebugAsync(tenantId, entityId, "OUT", tbMsg, relationType, null);
    }

    private void persistDebugAsync(TenantId tenantId, EntityId entityId, String type, TbMsg tbMsg, String relationType, Throwable error) {
        try {
            Event event = new Event();
            event.setTenantId(tenantId);
            event.setEntityId(entityId);
            event.setType(DataConstants.DEBUG_RULE_NODE);

            String metadata = mapper.writeValueAsString(tbMsg.getMetaData().getData());

            ObjectNode node = mapper.createObjectNode()
                    .put("type", type)
                    .put("server", getServerAddress())
                    .put("entityId", tbMsg.getOriginator().getId().toString())
                    .put("entityName", tbMsg.getOriginator().getEntityType().name())
                    .put("msgId", tbMsg.getId().toString())
                    .put("msgType", tbMsg.getType())
                    .put("dataType", tbMsg.getDataType().name())
                    .put("relationType", relationType)
                    .put("data", tbMsg.getData())
                    .put("metadata", metadata);

            if (error != null) {
                node = node.put("error", toString(error));
            }

            event.setBody(node);
            ListenableFuture<Event> future = eventService.saveAsync(event);
            Futures.addCallback(future, new FutureCallback<Event>() {
                @Override
                public void onSuccess(@Nullable Event event) {

                }

                @Override
                public void onFailure(Throwable th) {
                    log.error("Could not save debug Event for Node", th);
                }
            });
        } catch (IOException ex) {
            log.warn("Failed to persist rule node debug message", ex);
        }
    }

    public static Exception toException(Throwable error) {
        return Exception.class.isInstance(error) ? (Exception) error : new Exception(error);
    }

*/}