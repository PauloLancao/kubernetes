package com.prototype.restdocopenapi;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.prototype.kafka.KafkaProducerFoo;

public class FooListener {

	private static Log log = LogFactory.getLog(FooListener.class);
    
	private KafkaProducerFoo kafkaFooService;
		
	@Autowired
	public FooListener(KafkaProducerFoo kafkaFooService) {
		this.kafkaFooService = kafkaFooService;
		
		log.info("Initializing with dependency ["+ kafkaFooService +"]");
	}
	
    @PrePersist
    private void prePersistEvent(Foo foo) {
        
    	log.info("Before any persist in Foo table");
        
    	kafkaFooService.sendMessage(foo);
    }
    
    @PreUpdate
    private void preUpdateEvent(Foo foo) {
        
    	log.info("Before any update in Foo table");
        
    	kafkaFooService.sendMessage(foo);
    }
    
    @PreRemove
    private void preRemoveEvent(Foo foo) {
        
    	log.info("Before any remove in Foo table");
        
    	kafkaFooService.sendMessage(foo);
    }
}
