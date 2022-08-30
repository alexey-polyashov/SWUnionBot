package bot.union.sw.services;

import bot.union.sw.botScenarios.CommonScenario;
import bot.union.sw.botScenarios.SimpleScManagerConfig;
import bot.union.sw.botScenarios.StageParams;
import bot.union.sw.common.scenariodefine.Scenario;
import bot.union.sw.entities.BotUser;
import bot.union.sw.exceptions.SWUException;
import bot.union.sw.repository.UserStackRepository;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;
import java.util.Stack;

@Service
@RequiredArgsConstructor
@Slf4j
@RequestScope
public class BotService {

    private final SimpleScManagerConfig simpleScManager;
    private final BotUserService botUserService;
    private final ScenarioService scenarioService;
    private final UserStackRepository userStackRepository;

    private Chat currentChat;

    public Chat getCurrentChat() {
        return currentChat;
    }

    public void setCurrentChat(Chat currentChat) {
        this.currentChat = currentChat;
    }

    Stack<Scenario<String, StageParams>> scenarioStack = new Stack<>();

    public Scenario<String, StageParams> startScenario(String scId, Chat chat) throws Throwable {
        log.info("Get scenario for id - {}, in chat:{}", scId, chat.id());
        CommonScenario<String, StageParams> sc = (CommonScenario<String, StageParams>)simpleScManager.getScenarioById(scId)
                .orElseThrow(()->new SWUException(String.format("chat:%1$S, Не найден сценарий по id - %2$S", chat.id(), scId)));
        sc.setBotService(this);
        sc.setScenarioService(scenarioService);
        sc.start();
        return sc;
    }

    public Optional<BotUser> getUser(String userId, Chat chat){
        return botUserService.getUser(userId, chat);
    }

    public void saveStack(Chat chat){
        
    }

    public void loadStack(Chat chat){

    }

    public void load() {
    }

    public void save() {
        saveStack(currentChat);
    }

    public void doWork(String mes, TelegramBot bot, Chat chat, Message fullMes, TelegramRequest request) {

        bot.execute(new SendMessage(chat.id(), "Добро пожаловать!"));
        bot.execute(new SendMessage(chat.id(), "Я бот компании 'Спецобъединение Северо-Запад'"));
        bot.execute(new SendMessage(chat.id(), "Я пока ничего не умею, но скоро меня научат"));


    }
}
