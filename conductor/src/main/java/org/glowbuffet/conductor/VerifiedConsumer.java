package org.glowbuffet.conductor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VerifiedConsumer {
    private final OutboundProducer outbound;
    private final Logger logger = LoggerFactory.getLogger(VerifiedConsumer.class);
    private static final String VERIFIED_TOPIC = "verified";

    @Autowired
    VerifiedConsumer(OutboundProducer outbound) {
        this.outbound = outbound;
    }

    @KafkaListener(topics = VERIFIED_TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        this.outbound.sendMessage(message);
    }
}
