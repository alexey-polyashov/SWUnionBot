package bot.union.sw.botScenarios;

import bot.union.sw.common.scenariodefine.Scenario;
import bot.union.sw.common.scenariodefine.simplescenario.SimpleScenarioStage;
import bot.union.sw.entities.BotUser;
import bot.union.sw.exceptions.SWUException;
import bot.union.sw.services.BotService;
import bot.union.sw.services.BotUserService;
import bot.union.sw.services.ScenarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class MainMenuScenario extends CommonScenario<String, StageParams> {

    private ScenarioService scenarioService;
    private BotService botService;
    private BotUserService botUserService;


    public MainMenuScenario() {
        super();
        setScenarioId("MainMenuScenario");
    }

    public void setScenarioService(ScenarioService scenarioService, BotUserService botUserService){
        this.scenarioService = scenarioService;
        this.botUserService = botUserService;
    }

    public void setBotService(BotService botService){
        this.botService = botService;
    }

    @Override
    public void init() {

        SimpleScenarioStage<String, StageParams> st1 = new SimpleScenarioStage<>("1", (p) -> {
            String messageText = p.getMessage().text();
            Optional<BotUser> botUser = botUserService.getUserByChat(p.getChat());
            if(messageText.equals(str_main_menu_authorization) || messageText.equals(str_main_menu_authorization_cmd)){
                if(!botUser.isEmpty()){
                    p.getBot().execute(new SendMessage(p.getChat(), "Вы уже авторизованы").parseMode(ParseMode.HTML));
                }else{
                    Scenario<String, StageParams> sc = botService.startScenario("NewUserConnectedScenario", p.getChat());
                    StageParams newP = StageParams.builder()
                            .bot(p.getBot())
                            .chat(p.getChat())
                            .message(p.getMessage())
                            .request(p.getRequest())
                            .build();
                    sc.doWork(newP);
                    botService.checkScenStack();
                }
            }else if(messageText.equals(str_main_menu_help) || messageText.equals(str_main_menu_cmd)){
                p.getBot().execute(new SendMessage(p.getChat(),
                        "Для выбора действия используйте основное меню. Основное меню расположено под полем ввода." +
                                "Для показа/скрытия меню используйте специальную кнопку в поле ввода" +
                                "Выбрав нужное действие, выполняйте инструкции." +
                                "Для возврата в основное меню выберите действие 'Возврат'").parseMode(ParseMode.HTML));
            }else if(messageText.equals(str_main_menu_settings) || messageText.equals(str_main_menu_settings_cmd)){
                Scenario<String, StageParams> sc = botService.startScenario("SettingScenario", p.getChat());
                StageParams newP = StageParams.builder()
                        .bot(p.getBot())
                        .chat(p.getChat())
                        .message(p.getMessage())
                        .request(p.getRequest())
                        .build();
                sc.doWork(newP);
                botService.checkScenStack();
            }else if(messageText.equals(str_main_menu_new_querry) || messageText.equals(str_main_menu_new_querry_cmd)){
                Scenario<String, StageParams> sc = botService.startScenario("NewQuerryScenario", p.getChat());
                StageParams newP = StageParams.builder()
                        .bot(p.getBot())
                        .chat(p.getChat())
                        .message(p.getMessage())
                        .request(p.getRequest())
                        .build();
                sc.doWork(newP);
                botService.checkScenStack();
            }else if(messageText.equals(str_main_menu_my_querries) || messageText.equals(str_main_menu_my_querries_cmd)){
                Scenario<String, StageParams> sc = botService.startScenario("MyQuerriesScenario", p.getChat());
                StageParams newP = StageParams.builder()
                        .bot(p.getBot())
                        .chat(p.getChat())
                        .message(p.getMessage())
                        .request(p.getRequest())
                        .build();
                sc.doWork(newP);
                botService.checkScenStack();
            }
            return null;
        });

        addStage(st1);

    }

    @Override
    public void finish() {
        botService.endCurrentScenario();
    }

    @Override
    public String toString() {
        return "{" +
                "\"currentStage\":\"" + getCurrentStage().getIdentifier() + "\"," +
                "\"started\":\"" + isStarted() + "\"," +
                "\"done\":\"" + isDone() + "\"" +
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
