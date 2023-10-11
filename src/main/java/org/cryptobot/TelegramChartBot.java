package org.cryptobot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramChartBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        if (!msg.getText().equals("/sezam")) return;

        try {
            var charts = ChartService.getCharts();
            sendText(id, ChartService.assembleUrl(charts));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    @Override
    public String getBotUsername() {
        return "bidgalbot";
    }

    @Override
    public String getBotToken() {
        return "6357720891:AAGh7jCtXOCUhJoq_FSTeGdhkzHzq6arf6o";
    }
}
