package org.glowbuffet.apigateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glowbuffet.apigateway.tele.BotController;
import org.glowbuffet.common.dto.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.glowbuffet.common.TopicConstants.OUTBOUND_TOPIC;

@Service
public class OutboundConsumer {

    private final Logger logger = LoggerFactory.getLogger(OutboundConsumer.class);
    private String latestMessage;
    private final BotController botController;

    @Autowired
    OutboundConsumer(BotController botController) {
        this.botController = botController;
    }

    @KafkaListener(topics = OUTBOUND_TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info("#### -> Consumed message -> {}", message);
        latestMessage = message;
        Resolution resolution = new ObjectMapper().readValue(message, Resolution.class);
        botController.sendUpdates(resolution.getChatId(), resolution.getText());
    }

    public String getLatestMessage() {
        return latestMessage;
    }
}
