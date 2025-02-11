package org.glowbuffet.apigateway;

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
    public Outbound getLatestOutbound() {
        return new Outbound(123, this.consumer.getLatestMessage());
    }

    @PostMapping("/inbound")
    @ResponseStatus(HttpStatus.CREATED)
    public void addInbound(@RequestBody Inbound inbound) {
        this.producer.sendMessage(inbound.content());
    }
}
