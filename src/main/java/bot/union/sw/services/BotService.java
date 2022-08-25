package bot.union.sw.services;

import bot.union.sw.botScenarios.StageParams;
import bot.union.sw.common.scenariodefine.Scenario;
import bot.union.sw.common.scenariodefine.simplescenario.SimpleScManager;
import bot.union.sw.entyties.BotUser;
import bot.union.sw.exceptions.SWUException;
import com.pengrad.telegrambot.model.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;


import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Stack;

@Service
@RequiredArgsConstructor
@Slf4j
@RequestScope
public class BotService {

    private final SimpleScManager<String, StageParams> simpleScManager;
    private final BotUserService botUserService;

    Stack<Scenario<String, StageParams>> scenarioStack = new Stack<>();

    @PostConstruct
    private void init(){

    }

    public Scenario<String, StageParams> startScenario(String scId, Chat chat) throws Throwable {
        log.info("Get scenario for id - {}, in chat:{}", scId, chat.id());
        Scenario<String, StageParams> sc = (Scenario<String, StageParams>)simpleScManager.getScenarioById(scId)
                .orElseThrow(()->new SWUException(String.format("chat:%1$S, Не найден сценарий по id - %2$S", chat.id(), scId)));
        sc.start();
        return sc;
    }

    public Optional<BotUser> getUser(String userId, Chat chat){
        return botUserService.getUser(userId, chat);
    }


}
