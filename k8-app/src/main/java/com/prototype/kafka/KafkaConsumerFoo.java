package com.prototype.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import com.prototype.restdocopenapi.Foo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumerFoo {
	
	@KafkaListener(topics = "foo-topic", groupId = "foo-group", containerFactory = "kafkaFooListenerContainerFactory")
	public void listenGroupFoo(Foo message,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset) {
	    
		log.info("Received Message in group foo: " + message);
		log.info("Received Message in group foo partition: " + partition);
		log.info("Received Message in group foo offset: " + offset);
	}
}
