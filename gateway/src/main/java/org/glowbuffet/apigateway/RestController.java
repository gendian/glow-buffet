package org.glowbuffet.apigateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glowbuffet.common.dto.Command;
import org.glowbuffet.common.dto.Resolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final InboundProducer producer;
    private final OutboundConsumer consumer;

    @Autowired
    RestController(InboundProducer producer, OutboundConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("/outbound")
    public Outbound getLatestOutbound() throws JsonProcessingException {
        Resolution resolution = new ObjectMapper().readValue(this.consumer.getLatestMessage(), Resolution.class);
        return new Outbound(123, resolution);
    }

    @PostMapping("/inbound")
    @ResponseStatus(HttpStatus.CREATED)
    public void addInbound(@RequestBody Command command) throws JsonProcessingException {
        this.producer.sendMessage(command);
    }
}
