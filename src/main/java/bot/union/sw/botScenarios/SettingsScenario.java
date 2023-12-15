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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SettingsScenario extends CommonScenario<String, StageParams> {

    private ScenarioService scenarioService;
    private BotService botService;

    public SettingsScenario() {
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
            bot.execute(new SendMessage(chat.id(), "<- Вы перешли в меню настроек"));
            //prepare settings menu
            Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                    new String[]{"Упарвление моими сервисами"},
                    new String[]{"Отключиться от чата"},
                    new String[]{"Главное меню"})
                    .oneTimeKeyboard(true)   // optional
                    .resizeKeyboard(true)    // optional
                    .selective(true);        // optional
            bot.execute(new SendMessage(chat, "").replyMarkup(replyKeyboardMarkup));
            return "2";
        });

        SimpleScenarioStage<String, StageParams> st2 = new SimpleScenarioStage<>("2", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            String message = p.getRequest().getText();
            InlineKeyboardButton kbReturn = new  InlineKeyboardButton("< Возврат").callbackData("ret");
            switch (message) {
                case "delServices":
                    goToStage("3");
                    doWork(p);
                    return "3"; //шаг удаления
                case "addServices":
                    goToStage("4");
                    doWork(p);
                    return "4";
                case "ret":
                    bot.execute(new SendMessage(chat.id(), "Вы вернулись в чат"));
                    return null;
                default:
                    bot.execute(new SendMessage(chat.id(), "Извините, я вас не понял"));
                    bot.execute(new SendMessage(chat.id(), "Выберите одно из доступных действий с помощью кнопок"));
                    goToStage("1");
                    doWork(p);
                    return "1";
            }
        });


        SimpleScenarioStage<String, StageParams> st3 = new SimpleScenarioStage<>("3", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            String message = p.getRequest().getText();
            bot.execute(new SendMessage(chat.id(), "<- Управление сервисами"));
            InlineKeyboardButton kbReturn = new  InlineKeyboardButton("< Возврат").callbackData("ret");

            bot.execute(new SendMessage(chat.id(), "<-- Отключение сервисов"));
            SendMessage sm = new SendMessage(chat.id(), "Выберите сервис для <u>О</u>тключения").parseMode(ParseMode.HTML);
            List<AllowService> servicesList = botService.findAllUserServices(chat);
            int lineCounter = servicesList.size();
            for (AllowService aServ: servicesList) {
                lineCounter--;
                InlineKeyboardButton kbDelServ = new  InlineKeyboardButton("<u>" + aServ.getName() + "</u> - <i>" + aServ.getDescription() + "</i>" + aServ.getName()).callbackData("del#" + aServ.getName());
                sm.replyMarkup(new InlineKeyboardMarkup(kbDelServ));
                if(lineCounter==0){
                    sm.replyMarkup(new InlineKeyboardMarkup(kbReturn));
                }
            }
            bot.execute(sm);
            return "5"; //шаг удаления

        });

        SimpleScenarioStage<String, StageParams> st4 = new SimpleScenarioStage<>("4", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            String message = p.getRequest().getText();
            InlineKeyboardButton kbReturn = new  InlineKeyboardButton("< Возврат").callbackData("ret");
            switch (message) {
                case "addServices":
                    List<AllowService> allowServices = botService.findAllowServices(chat);
                    if (allowServices.size() == 0) {
                        bot.execute(new SendMessage(chat.id(), "*Нет доступных для подключения сервисов*").parseMode(ParseMode.MarkdownV2));
                        goToStage("1");
                        doWork(p);
                        return "1";
                    } else {
                        bot.execute(new SendMessage(chat.id(), "<- Управление сервисами"));
                        bot.execute(new SendMessage(chat.id(), "<-- Подключение сервисов"));
                        SendMessage smAdd = new SendMessage(chat.id(), "<b>Список доступных для подключения сервисов</b>").parseMode(ParseMode.HTML);
                        InlineKeyboardMarkup kbd = new InlineKeyboardMarkup();
                        for (AllowService aServ : allowServices) {
                            List<InlineKeyboardButton> row = new ArrayList<>();
                            kbd.addRow(new InlineKeyboardButton("<u>" + aServ.getName() + "</u> - <i>" + aServ.getDescription() + "</i>").callbackData("add#" + aServ.getName()));
                        }
                        kbd.addRow(kbReturn);
                        smAdd.replyMarkup(kbd);
                        bot.execute(smAdd);
                        return "6"; //шаг добавления
                    }
                case "ret":
                    bot.execute(new SendMessage(chat.id(), "Вы вернулись в чат"));
                    return null;
                default:
                    bot.execute(new SendMessage(chat.id(), "Извините, я вас не понял"));
                    bot.execute(new SendMessage(chat.id(), "Выберите одно из доступных действий с помощью кнопок"));
                    return "4";
            }
        });

        SimpleScenarioStage<String, StageParams> st5 = new SimpleScenarioStage<>("5", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            String message = p.getRequest().getText();
            String[] mesParts = message.split("#");
            switch (mesParts[0]) {
                case "del":
                    botService.deleteUserService(chat, mesParts[1]);
                    bot.execute(new SendMessage(chat.id(), "Сервис '" + mesParts[1] + "' отключен"));
                    goToStage("3");
                    doWork(p);
                    return "3";
               case "ret":
                   goToStage("1");
                   doWork(p);
                   return "1";
                default:
                    bot.execute(new SendMessage(chat.id(), "Извините, я вас не понял"));
                    bot.execute(new SendMessage(chat.id(), "Выберите одно из доступных действий с помощью кнопок"));
                    return "5";
            }
        });


        SimpleScenarioStage<String, StageParams> st6 = new SimpleScenarioStage<>("6", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            String message = p.getRequest().getText();
            String[] mesParts = message.split("#");
            switch (mesParts[0]) {
                case "add":
                    if(mesParts.length>1 && !mesParts[1].isEmpty()){
                        botService.addUserService(chat, mesParts[1]);
                        bot.execute(new SendMessage(chat.id(), "Сервис '" + mesParts[1] + "' подключен"));
                        goToStage("4");
                        doWork(p);
                        return "4";
                    }
                case "ret":
                    goToStage("1");
                    doWork(p);
                    return "1";
                default:
                    bot.execute(new SendMessage(chat.id(), "Извините, я вас не понял"));
                    bot.execute(new SendMessage(chat.id(), "Выберите одно из доступных действий с помощью кнопок"));
                    return "6";
            }
        });

        addStage(st1);
        addStage(st2);
        addStage(st3);
        addStage(st4);
        addStage(st5);
        addStage(st6);

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
