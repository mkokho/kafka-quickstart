package com.example.demo;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.UUID;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private ApplicationConfig config;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * Send an object serialized as JSON to a predefined topic.
     *
     * @param object - object to send.
     */
    public void sendEvent(Object object) {
        LOGGER.debug("sending event [{}]", object.toString() );
        kafkaTemplate.send(config.sensorTopic, new Key(), object);
    }

    @PreDestroy
    public void destroy()
    {
        kafkaTemplate.flush();
    }

    /**
     * Unique random key of an event.
     */
    private static class Key {
        private final String id;

        private Key() {
            this.id = UUID.randomUUID().toString();
        }

        public String getId() {
            return id;
        }
    }
}
