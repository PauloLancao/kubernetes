package com.prototype.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.prototype.restdocopenapi.Moo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumerMoo {
	
	@KafkaListener(topics = "moo-topic", groupId = "moo-group", containerFactory = "kafkaMooListenerContainerFactory")
	public void listenGroupMoo(Moo message,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset) {

		log.info("Received Message in group moo: " + message);
		log.info("Received Message in group moo partition: " + partition);
		log.info("Received Message in group moo offset: " + offset);
	}
}
