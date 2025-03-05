package org.glowbuffet.apigateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.glowbuffet.common.dto.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.glowbuffet.common.TopicConstants.INBOUND_TOPIC;

@Service
public class InboundProducer {

    private static final Logger logger = LoggerFactory.getLogger(InboundProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Command command) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(command);
        logger.info("#### -> Producing message -> {}", json);
        this.kafkaTemplate.send(INBOUND_TOPIC, json);
    }
}
