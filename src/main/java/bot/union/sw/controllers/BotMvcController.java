package bot.union.sw.controllers;

import bot.union.sw.BotConfig;
import bot.union.sw.services.BotService;
import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class BotMvcController  implements TelegramMvcController {

    private final String GET_START = "/start";

    @Autowired
    private BotConfig botConfig;
    @Autowired
    private BotService botService;

    @Override
    public String getToken() {
        return botConfig.getToken();
    }

    //для сообщений
    @BotRequest(type = {MessageType.ANY})
    public void optionMethod(String mes, TelegramBot bot, Chat chat, Message fullMes, TelegramRequest request) throws Throwable {

        baseMethod(mes, bot, chat, fullMes, request);

    }

    //для команд
    @MessageRequest("/*")
    public void commandRequest(String mes, TelegramBot bot, Chat chat, Message fullMes, TelegramRequest request) throws Throwable {

        baseMethod(mes, bot, chat, fullMes, request);

    }

    private void baseMethod(String mes, TelegramBot bot, Chat chat, Message fullMes, TelegramRequest request) throws Throwable {

        botService.setCurrentChat(chat);
        botService.loadChat();
        botService.doWork(mes, bot, chat, fullMes, request);
        botService.saveChat();

    }

}
