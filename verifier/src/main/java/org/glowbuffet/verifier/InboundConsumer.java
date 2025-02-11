package org.glowbuffet.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InboundConsumer {
    private final VerifiedProducer verified;
    private final WelcomeProducer welcome;

    @Autowired
    InboundConsumer(VerifiedProducer verified, WelcomeProducer welcome) {
        this.verified = verified;
        this.welcome = welcome;
    }
    private final Logger logger = LoggerFactory.getLogger(InboundConsumer.class);
    private static final String INBOUND_TOPIC = "inbound";

    @KafkaListener(topics = INBOUND_TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        if (verifiedNumber()) {
            this.verified.sendMessage(message);
        } else {
            this.welcome.sendMessage(message);
        }
    }

    private boolean verifiedNumber() {
        return true;
    }
}
