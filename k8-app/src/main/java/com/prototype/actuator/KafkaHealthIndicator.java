package com.prototype.actuator;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.Status;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaHealthIndicator extends AbstractHealthIndicator {

	static final String REPLICATION_PROPERTY = "transaction.state.log.replication.factor";

	@Autowired
	private KafkaAdmin kafkaAdmin;

	private Integer TIMEOUT_MS = 1000;

	private DescribeClusterOptions describeOptions = new DescribeClusterOptions().timeoutMs(TIMEOUT_MS);

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		try (AdminClient adminClient = AdminClient.create(this.kafkaAdmin.getConfigurationProperties())) {
			DescribeClusterResult clusterResult = adminClient.describeCluster(this.describeOptions);

			// print detail of each of the nodes
			adminClient.describeCluster(this.describeOptions).nodes().get().stream().forEach(node -> {
				try {
					int replicationNum = getReplicationNumber(node.id() + "", adminClient);
					log.info("Traversing Kafka Node: Node.ID = {}, host = {}, port = {}, replicationnum = {}",
							node.id(), node.host(), node.port(), replicationNum);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			});

			// Kafka acts as a controller's node The Node as Controller Node
			String brokerId = clusterResult.controller().get(TIMEOUT_MS, TimeUnit.MILLISECONDS).idString();
			int replicationNum = getReplicationNumber(brokerId, adminClient);
			int availableNodesNum = clusterResult.nodes().get().size();
			Status status = availableNodesNum >= replicationNum ? Status.UP : Status.DOWN;
			builder.status(status).withDetail("clusterId", clusterResult.clusterId().get())
					.withDetail("controller-node-brokerId", brokerId)
					.withDetail("available-nodes-number", availableNodesNum);
		}
	}

	private int getReplicationNumber(String brokerId, AdminClient adminClient)
			throws ExecutionException, InterruptedException {
		ConfigResource configResource = new ConfigResource(ConfigResource.Type.BROKER, brokerId);
		String replicationNumStr = adminClient.describeConfigs(Collections.singletonList(configResource)).all().get()
				.get(configResource).get(REPLICATION_PROPERTY).value();
		return Integer.valueOf(replicationNumStr);
	}

}
