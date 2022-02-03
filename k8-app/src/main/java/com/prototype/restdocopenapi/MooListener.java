package com.prototype.restdocopenapi;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.prototype.kafka.KafkaProducerMoo;

public class MooListener {

	private static Log log = LogFactory.getLog(MooListener.class);
    
	private KafkaProducerMoo kafkaMooService;
	
	@Autowired
	public MooListener(KafkaProducerMoo kafkaMooService) {
		this.kafkaMooService = kafkaMooService;
		
		log.info("Initializing with dependency ["+ kafkaMooService +"]");
	}
	
    @PreRemove
    private void preRemoveEvent(Moo moo) {
        
    	log.info("Before any remove in Moo table");
        
    	kafkaMooService.sendMessage(moo);
    }
    
    @PrePersist
    private void prePersistEvent(Moo moo) {
        
    	log.info("Before any persist in Moo table");
        
    	kafkaMooService.sendMessage(moo);
    }
    
    @PreUpdate
    private void preUpdateEvent(Moo moo) {
        
    	log.info("Before any update in Moo table");
        
    	kafkaMooService.sendMessage(moo);
    }
}
