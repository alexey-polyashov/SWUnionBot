package bot.union.sw.botScenarios;

import bot.union.sw.common.scenariodefine.simplescenario.SimpleScenarioStage;
import bot.union.sw.entities.AllowService;
import bot.union.sw.exceptions.SWUException;
import bot.union.sw.services.BotService;
import bot.union.sw.services.ScenarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceSelectScenario extends CommonScenario<String, StageParams> {

    private ScenarioService scenarioService;
    private BotService botService;

    public ServiceSelectScenario() {
        super();
        setScenarioId("ServiceSelectScenario");
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
            bot.execute(new SendMessage(chat.id(), "<---"));
            bot.execute(new SendMessage(chat.id(), "*Список подключенных у вас сервисов*").parseMode(ParseMode.MarkdownV2));
            List<AllowService> servicesList = botService.findAllUserServices(chat);
            InlineKeyboardButton kbAddServ = new  InlineKeyboardButton("+ Добавить сервис из списка доступных").callbackData("add#");
            InlineKeyboardButton kbReturn = new  InlineKeyboardButton("< Назад").callbackData("ret#");
            if(servicesList.size()==0){
                bot.execute(new SendMessage(chat.id(),"*У вас нет подключенных сервисов!*").parseMode(ParseMode.MarkdownV2));
            }else{
                for (AllowService aServ: servicesList) {
                    InlineKeyboardButton kbDelServ = new  InlineKeyboardButton("- Удалить сервис - " + aServ.getName()).callbackData("del#" + aServ.getName());
                    bot.execute(new SendMessage(chat.id(), "<u>" + aServ.getName() + "</u> - <i>" + aServ.getDescription() + "</i>").parseMode(ParseMode.HTML)
                            .replyMarkup(new InlineKeyboardMarkup(kbDelServ)));
                }
            }
            bot.execute(new SendMessage(chat.id(), "--->")
                    .replyMarkup(new InlineKeyboardMarkup(kbAddServ,kbReturn)));
            return "2";
        });

        SimpleScenarioStage<String, StageParams> st2 = new SimpleScenarioStage<>("2", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            String message = p.getRequest().getText();
            String[] mesParts = message.split("#");
            switch (mesParts[0]) {
                case "del":
                    botService.deleteUserService(chat, mesParts[1]);
                    bot.execute(new SendMessage(chat.id(), "Сервис '" + mesParts[1] + "' отключен"));
                    goToStage("1");
                    doWork(p);
                    return "2";
                case "add":
                    if(mesParts.length>1 && !mesParts[1].isEmpty()){
                        botService.addUserService(chat, mesParts[1]);
                        bot.execute(new SendMessage(chat.id(), "Сервис '" + mesParts[1] + "' подключен"));
                        goToStage("1");
                        doWork(p);
                        return "2";
                    }
                    bot.execute(new SendMessage(chat.id(), "<---"));
                    bot.execute(new SendMessage(chat.id(), "*Список доступных для подключения сервисов*").parseMode(ParseMode.MarkdownV2));
                    List<AllowService> servicesList = botService.findAllowServices(chat);
                    InlineKeyboardButton kbReturn = new InlineKeyboardButton("< Назад").callbackData("ret#");
                    if (servicesList.size() == 0) {
                        bot.execute(new SendMessage(chat.id(), "*Все доступные сервисы уже подключены!*").parseMode(ParseMode.MarkdownV2));
                    } else {
                        for (AllowService aServ : servicesList) {
                            InlineKeyboardButton kbAddServ = new InlineKeyboardButton("+ Добавить сервис - " + aServ.getName()).callbackData("add#" + aServ.getName());
                            bot.execute(new SendMessage(chat.id(), "<u>" + aServ.getName() + "</u> - <i>" + aServ.getDescription() + "</i>").parseMode(ParseMode.HTML)
                                    .replyMarkup(new InlineKeyboardMarkup(kbAddServ)));
                        }
                    }
                    bot.execute(new SendMessage(chat.id(), "--->")
                            .replyMarkup(new InlineKeyboardMarkup(kbReturn)));
                    return "3";
                case "ret":
                    bot.execute(new SendMessage(chat.id(), "Вы вышли из настроек сервисов"));
                    return null;
                default:
                    bot.execute(new SendMessage(chat.id(), "Извините, я вас не понял"));
                    bot.execute(new SendMessage(chat.id(), "Выберите одно из доступных действий с помощью кнопок"));
                    return "2";
            }
        });

        SimpleScenarioStage<String, StageParams> st3 = new SimpleScenarioStage<>("3", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            String message = p.getRequest().getText();
            String[] mesParts = message.split("#");
            switch (mesParts[0]) {
                case "add":
                    botService.addUserService(chat, mesParts[1]);
                    bot.execute(new SendMessage(chat.id(), "Сервис '" + mesParts[1] + "' подключен"));
                    goToStage("1");
                    doWork(p);
                    return "2";
                case "ret":
                    bot.execute(new SendMessage(chat.id(), "Вы вернулись к настройке своих сервисов"));
                    goToStage("1");
                    doWork(p);
                    return "2";
                default:
                    bot.execute(new SendMessage(chat.id(), "Извините, я вас не понял"));
                    bot.execute(new SendMessage(chat.id(), "Выберите выберите сервис для добавления"));
                    return "3";
            }
        });

        addStage(st1);
        addStage(st2);
        addStage(st3);

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
        Map<String, String> mapped;
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
