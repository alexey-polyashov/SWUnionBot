package bot.union.sw.services;

import bot.union.sw.botScenarios.CommonScenario;
import bot.union.sw.botScenarios.SimpleScManagerConfig;
import bot.union.sw.botScenarios.StageParams;
import bot.union.sw.common.scenariodefine.Scenario;
import bot.union.sw.entities.BotUser;
import bot.union.sw.entities.UserStack;
import bot.union.sw.exceptions.SWUException;
import bot.union.sw.exceptions.ScenarioMissing;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;

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

    public Scenario<String, StageParams> startScenario(String scId, Chat chat) {
        log.info("Get scenario for id - {}, in chat:{}", scId, chat.id());
        CommonScenario<String, StageParams> sc = (CommonScenario<String, StageParams>)simpleScManager.getScenarioById(scId)
                .orElseThrow(()->new ScenarioMissing(String.format("chat:%1$S, Не найден сценарий по id - %2$S", chat.id(), scId)));
        sc.setBotService(this);
        sc.setScenarioService(scenarioService);
        scenarioStack.push(sc);
        sc.start();
        return sc;
    }

    public Optional<BotUser> getUser(String userId, Chat chat){
        return botUserService.getUser(userId, chat);
    }

    public void loadChat() {
        scenarioStack.clear();
        List<UserStack> loadList = userStackRepository.findByChatIdOrderByNumber(currentChat.id());
        Iterator<UserStack> it = loadList.listIterator();
        while(it.hasNext()){
            UserStack el = it.next();
            Scenario<String, StageParams> newScen = simpleScManager.getScenarioById(el.getScenarioId())
                    .orElseThrow(()->new SWUException("Ошибка восстановления сеанса, сценарий '" + el.getScenarioId() + "' не найден"));
            newScen.load(currentChat.id());
            scenarioStack.push(newScen);
        }
    }

    @Transactional
    public void saveChat() {
        int order = 1;
        Iterator<Scenario<String, StageParams>> it = scenarioStack.iterator();
        scenarioService.deleteByChatId(currentChat.id());
        userStackRepository.deleteByChatId(currentChat.id());
        while(it.hasNext()){
            Scenario<String, StageParams> scen = it.next();
            scen.save();
            UserStack userStack = UserStack.builder()
                    .number(order++)
                    .chatId(currentChat.id())
                    .scenarioId(scen.getId())
                    .build();
            userStackRepository.save(userStack);
        }
    }

    public void doWork(String mes, TelegramBot bot, Chat chat, Message fullMes, TelegramRequest request) throws Throwable {

        if(scenarioStack.empty()) {
            Optional<BotUser> botUser = botUserService.getUserByChat(chat);
            if (botUser.isEmpty()) {
                Scenario<String, StageParams> sc = startScenario("NewUserConnectedScenario", currentChat);
                StageParams p = StageParams.builder().bot(bot).chat(chat).message(fullMes).request(request).build();
                sc.doWork(p);
                return;
            }else{
                bot.execute(new SendMessage(chat.id(), "Здравствуйте!"));
                bot.execute(new SendMessage(chat.id(), "Я бот компании Спецобъединение \"Северо-Запад\""));
                bot.execute(new SendMessage(chat.id(), "Я пока ничего не умею, но скоро меня научат"));
            }
        }else{
            Scenario<String, StageParams> sc = scenarioStack.peek();
            StageParams p = StageParams.builder().bot(bot).chat(chat).message(fullMes).request(request).build();
            sc.doWork(p);
            return;
        }

    }

    public void endCurrentScenario() {
        if (!scenarioStack.empty()){
            scenarioStack.pop();
        }
    }
}
