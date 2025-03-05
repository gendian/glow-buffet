package org.glowbuffet.greeter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.glowbuffet.common.TopicConstants;
import org.glowbuffet.common.dto.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OutboundProducer {

    private static final Logger logger = LoggerFactory.getLogger(OutboundProducer.class);
    private static final String TOPIC = "outbound";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Resolution resolution) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(resolution);
        logger.info("#### -> Producing message -> {}", json);
        this.kafkaTemplate.send(TopicConstants.OUTBOUND_TOPIC, json);
    }
}
