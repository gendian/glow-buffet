package org.glowbuffet.greeter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glowbuffet.common.TopicConstants;
import org.glowbuffet.common.dto.Command;
import org.glowbuffet.common.dto.Resolution;
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

    @Autowired
    WelcomeConsumer(OutboundProducer outbound) {
        this.outbound = outbound;
    }

    @KafkaListener(topics = TopicConstants.WELCOME_TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info("#### -> Consumed message -> {}", message);
        Resolution resolution = resolveMessage(message);
        this.outbound.sendMessage(resolution);
    }

    private Resolution resolveMessage(String message) throws JsonProcessingException {
        Command command = new ObjectMapper().readValue(message, Command.class);
        return new Resolution(command);
    }
}
