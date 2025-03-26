package org.glowbuffet.apigateway.tele;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.glowbuffet.apigateway.InboundProducer;
import org.glowbuffet.common.dto.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotController {

    private final boolean telegramEnabled = false;
    private final String botToken = "7540929494:AAENhozzw-E8jTvI19WiRBmeKzF2ipzM-kA";

    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    private final InboundProducer producer;
    private TelegramBot bot;

    @Autowired
    BotController(InboundProducer producer) {
        this.producer = producer;
        register();
    }

    private void register() {
        bot = new TelegramBot(botToken);
        receiveUpdates(bot);
    }

    public void sendUpdates(long chatId, String text) {
        if (telegramEnabled) {
            SendMessage request = new SendMessage(chatId, text)
                    .parseMode(ParseMode.HTML)
                    .disableNotification(true)
                    .replyMarkup(new ForceReply());

            // sync
            SendResponse sendResponse = bot.execute(request);
            sendResponse.isOk();
            sendResponse.message();
        }
    }

    private void receiveUpdates(TelegramBot bot) {
        if (telegramEnabled) {
            bot.setUpdatesListener(updates -> {
                Message latestMessage = updates.get(updates.size() - 1).message();
                Command command = new Command(latestMessage);
                try {
                    producer.sendMessage(command);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }, e -> {
                if (e.response() != null) {
                    e.response().errorCode();
                    e.response().description();
                } else {
                    logger.error("#### -> Producing message -> {}", e.getMessage());
                }
            });
        }
    }
}
