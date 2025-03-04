package org.glowbuffet.apigateway;

import org.apache.kafka.clients.admin.NewTopic;
import org.glowbuffet.apigateway.tele.BotController;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ApiGatewayApplication {
    private static final String INBOUND_TOPIC = "inbound";

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(INBOUND_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send(INBOUND_TOPIC, "test");
        };
    }

    @KafkaListener(id = "myId", topics = INBOUND_TOPIC)
    public void listen(String in) {
        System.out.println(in);
    }

}
