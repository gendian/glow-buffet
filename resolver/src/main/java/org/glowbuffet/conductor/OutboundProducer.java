package org.glowbuffet.conductor;

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

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Resolution resolution) throws JsonProcessingException {
        resolution.setText(resolution.getText() + Math.random());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(resolution);
        logger.info("#### -> Producing message -> {}", json);
        this.kafkaTemplate.send(TopicConstants.OUTBOUND_TOPIC, json);
    }
}
