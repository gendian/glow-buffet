package org.glowbuffet.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OutboundConsumer {

    private final Logger logger = LoggerFactory.getLogger(OutboundConsumer.class);
    private static final String TOPIC = "outbound";
    private static String latestMessage;

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        latestMessage = message;
    }

    public String getLatestMessage() {
        return latestMessage;
    }
}
