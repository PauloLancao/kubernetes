package com.prototype.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.prototype.restdocopenapi.Foo;
import com.prototype.restdocopenapi.Moo;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${kafka.consumer.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${foo.group.id}")
	private String fooGroupId;

	@Value(value = "${moo.group.id}")
	private String mooGroupId;

	@Bean
	public ConsumerFactory<String, Foo> consumerFooFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, fooGroupId);

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Foo.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Foo> kafkaFooListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, Foo> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFooFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, Moo> consumerMooFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, mooGroupId);

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Moo.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Moo> kafkaMooListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, Moo> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerMooFactory());
		return factory;
	}
}
