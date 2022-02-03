package com.prototype.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

	@Value(value = "${kafka.producer.bootstrapAddress}")
	private String bootstrapAddress;
	
	@Value(value = "${foo.topic.name}")
    private String fooTopicName;
	
	@Value(value = "${moo.topic.name}")
    private String mooTopicName;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic fooTopic() {
		return new NewTopic(fooTopicName, 1, (short) 1);
	}

	@Bean
	public NewTopic mooTopic() {
		return new NewTopic(mooTopicName, 1, (short) 1);
	}
}
