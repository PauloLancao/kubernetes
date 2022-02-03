package com.prototype.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.prototype.restdocopenapi.Foo;

@Component
public class KafkaProducerFoo {

	private static Log log = LogFactory.getLog(KafkaProducerFoo.class);
	
	@Autowired
	private KafkaTemplate<String, Foo> kafkaFooTemplate;

	@Value(value = "${foo.topic.name}")
	private String fooTopicName;

	public void sendMessage(Foo message) {
		ListenableFuture<SendResult<String, Foo>> future = kafkaFooTemplate.send(fooTopicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Foo>>() {

			@Override
			public void onSuccess(SendResult<String, Foo> result) {
				log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}
}
