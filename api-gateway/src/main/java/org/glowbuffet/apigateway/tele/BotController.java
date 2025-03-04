package org.glowbuffet.apigateway.tele;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import okhttp3.OkHttpClient;
import org.glowbuffet.apigateway.InboundProducer;
import org.glowbuffet.apigateway.OutboundConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BotController {

    private final InboundProducer producer;
    private final OutboundConsumer consumer;

    @Autowired
    BotController(InboundProducer producer, OutboundConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
        register();
    }

    public void register() {
        TelegramBot bot = new TelegramBot("7540929494:AAENhozzw-E8jTvI19WiRBmeKzF2ipzM-kA");

        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                String inboundMessageText = updates.get(updates.size() - 1).message().text();
                producer.sendMessage(inboundMessageText);

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        // Create Exception Handler
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } else {
                // probably network error
                e.printStackTrace();
            }
        });
    }
}
