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

import com.prototype.restdocopenapi.Moo;

@Component
public class KafkaProducerMoo {

	private static Log log = LogFactory.getLog(KafkaProducerMoo.class);

	@Autowired
	private KafkaTemplate<String, Moo> kafkaMooTemplate;

	@Value(value = "${moo.topic.name}")
	private String mooTopicName;

	public void sendMessage(Moo message) {
		ListenableFuture<SendResult<String, Moo>> future = kafkaMooTemplate.send(mooTopicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Moo>>() {

			@Override
			public void onSuccess(SendResult<String, Moo> result) {
				log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}
}
