package org.glowbuffet.verifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glowbuffet.common.TopicConstants;
import org.glowbuffet.common.dto.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

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

    @KafkaListener(topics = TopicConstants.INBOUND_TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        Command command = transformMessage(message);
        boolean isVerified = verifyUser(command.getId());
        logger.info("#### -> Consumed message -> {}", message);
        if (isVerified) {
            this.verified.sendMessage(command);
        } else {
            this.welcome.sendMessage(command);
        }
    }

    private Command transformMessage(String message) throws JsonProcessingException {
        return new ObjectMapper().readValue(message, Command.class);
    }

    private boolean verifyUser(UUID id) {
        // Check cache
        return true;
    }
}
