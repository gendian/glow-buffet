package org.glowbuffet.greeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WelcomeConsumer {
    private final OutboundProducer outbound;
    private final Logger logger = LoggerFactory.getLogger(WelcomeConsumer.class);
    private static final String INBOUND_TOPIC = "welcome";

    @Autowired
    WelcomeConsumer(OutboundProducer outbound) {
        this.outbound = outbound;
    }

    @KafkaListener(topics = INBOUND_TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        this.outbound.sendMessage(message);
    }
}
