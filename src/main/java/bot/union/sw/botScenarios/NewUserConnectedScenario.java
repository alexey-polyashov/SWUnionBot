package bot.union.sw.botScenarios;

import bot.union.sw.common.scenariodefine.simplescenario.SimpleScenarioStage;
import bot.union.sw.exceptions.SWUException;
import bot.union.sw.services.BotService;
import bot.union.sw.services.ScenarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.Map;


public class NewUserConnectedScenario extends CommonScenario<String, StageParams> {

    private ScenarioService scenarioService;
    private BotService botService;

    public NewUserConnectedScenario() {
        super();
    }

    public void setScenarioService(ScenarioService scenarioService){
        this.scenarioService = scenarioService;
    }

    public void setBotService(BotService botService){
        this.botService = botService;
    }

    @Override
    public void init() {

        SimpleScenarioStage<String, StageParams> st1 = new SimpleScenarioStage<>("1", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            bot.execute(new SendMessage(chat.id(), "Добро пожаловать!"));
            bot.execute(new SendMessage(chat.id(), "Введите адрес электронной почты (пример apetrov@spets.ru), или имя доменной учетной записи (пример apetrov)"));
            return "3";
        });

        SimpleScenarioStage<String, StageParams> st2 = new SimpleScenarioStage<>("2", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            bot.execute(new SendMessage(chat.id(), "Повторите ввод."));
            bot.execute(new SendMessage(chat.id(), "Введите адрес электронной почты (пример apetrov@spets.ru), или имя доменной учетной записи (пример apetrov)"));
            return "3";
        });

        SimpleScenarioStage<String, StageParams> st3 = new SimpleScenarioStage<>("3", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            bot.execute(new SendMessage(chat.id(), "Выполняю поиск ..."));
            //поиск пользователя
            if(botService
                    .getUser(p.getMessage(), p.getChat())
                    .isEmpty()) {
                bot.execute(new SendMessage(chat.id(), "Указанные вами идентификационные данные не соответствуют ни одному из пользователей. Повторите попытку."));
                return "2";
            }
            bot.execute(new SendMessage(chat.id(), "Вы зарегистрированы"));
            bot.execute(new SendMessage(chat.id(), "Используйте команду /myservices для управления сервисами"));
            bot.execute(new SendMessage(chat.id(), "Используйте команду /help для получения справки"));
            return null;
        });

        addStage(st1);
        addStage(st2);
        addStage(st3);

    }

    @Override
    public String toString() {
        return "{" +
                "\"currentStage\":\"" + getCurrentStage().getIdentifier() + "\"," +
                "\"started\":\"" + isStarted() + "\"," +
                "\"done\":\"" + isDone() + "\"," +
                "}";
    }

    @Override
    public long save() {
        String jsonData = toString();
        return scenarioService.saveScenario(botService.getCurrentChat().id(),this,  jsonData);
    }

    @Override
    public void load(long id) {
        String jsonData = "";
        Map<String, String> mapped = new HashMap<>();
        jsonData = scenarioService.restoreScenario(botService.getCurrentChat().id(), this);
        if(!jsonData.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                mapped = objectMapper.readValue(jsonData, Map.class);
            } catch (JsonProcessingException e) {
                throw new SWUException("Ошибка инициализации сценария" + getId());
            }
            String stageKey = mapped.get("currentStage");
            setCurrentStage(getStage(stageKey).orElseThrow(()->new SWUException("Не определен этап сценария " + stageKey)));
            setDone(Boolean.valueOf(mapped.get("done")));
            setStarted(Boolean.valueOf(mapped.get("started")));
        }
    }

}
