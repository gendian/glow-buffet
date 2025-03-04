package org.glowbuffet.verifier;

import org.glowbuffet.common.TopicConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WelcomeProducer {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TopicConstants.WELCOME_TOPIC, message);
    }

}
