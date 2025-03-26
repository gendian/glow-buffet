package org.glowbuffet.apigateway.whatsapp;

import com.pengrad.telegrambot.TelegramBot;
import org.glowbuffet.apigateway.InboundProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WhatsappController {

    private final boolean whatsappEnabled = true;
    private final String bearerToken = "EAAUvVDpZBldYBO7A3XYMnHexVRtqP9xVImEyftChj3kxFCRZCjZBiwM5jx78LqA4c7bZBU7LuZBRsiIAs5k1rBJvgdAIAfgzKoyhhZAqr0Co6hKd98ICez6aG68GEI5ZBBiY98j5obcpFPScaXtZAZAqNeAAwxOEgep3aIKZAH5kqChT6PJwkJU5vyXYaiTDCbazrptLVqp7DtbZBZAWEDsEMLQpgvxurCPbzBjnZAetxO2dcFgZDZD";
    private final String phoneNumberId = "599925339869630";

    private static final Logger logger = LoggerFactory.getLogger(org.glowbuffet.apigateway.tele.BotController.class);

    private final InboundProducer producer;
    private TelegramBot bot;

    @Autowired
    WhatsappController(InboundProducer producer) {
        this.producer = producer;
        send("447590285357", "message 3");
    }

    public void receive() {

    }

    public void send(String phoneNumber, String message) {
        if (whatsappEnabled) {
            String body = "{ \"messaging_product\": \"whatsapp\", " +
                    String.format("\"to\": \"%s\", ", phoneNumber) +
                    "\"type\": \"text\", " +
                    String.format("\"text\": { \"preview_url\": false, \"body\": \"%s\" } }", message);

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(String.format("https://graph.facebook.com/v22.0/%s/messages", phoneNumberId)))
                        .header("Authorization", String.format("Bearer %s", bearerToken))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
                HttpClient http = HttpClient.newHttpClient();
                HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());

            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
