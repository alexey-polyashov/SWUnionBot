package bot.union.sw;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class BotMvcController  implements TelegramMvcController {

    private final String GET_START = "/start";

    @Autowired
    private BotConfig botConfig;

    @Override
    public String getToken() {
        return botConfig.getToken();
    }

    @BotRequest(type = {MessageType.ANY})
    public void optionMethod(String mes, TelegramBot bot, Chat chat, Message fullMes, TelegramRequest request){
        bot.execute(new SendMessage(chat.id(), "Добро пожаловать!"));
        bot.execute(new SendMessage(chat.id(), "Я бот компании 'Объединение Северо-Запад'"));
        bot.execute(new SendMessage(chat.id(), "Я пока ничего не умею, но скоро меня научат"));
   }

}
