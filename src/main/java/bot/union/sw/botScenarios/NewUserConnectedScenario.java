package bot.union.sw.botScenarios;

import bot.union.sw.common.scenariodefine.simplescenario.SimpleScenario;
import bot.union.sw.common.scenariodefine.simplescenario.SimpleScenarioStage;
import bot.union.sw.services.ScenarioService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.request.SendMessage;

public class NewUserConnectedScenario extends SimpleScenario<String, StageParams> {

    private final ScenarioService scenarioService;

    public NewUserConnectedScenario(ScenarioService scenarioService) {
        super();
        this.scenarioService = scenarioService;
    }

    @Override
    public void init() {

        SimpleScenarioStage<String, StageParams> st1 = new SimpleScenarioStage<>("1", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            bot.execute(new SendMessage(chat.id(), "Добро пожаловать!"));
            bot.execute(new SendMessage(chat.id(), "Введите адрес электронной почты (пример apetrov@spets.ru), или имя доменной учетной записи (пример apetrov)"));
            return "2";
        });

        SimpleScenarioStage<String, StageParams> st2 = new SimpleScenarioStage<>("2", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            bot.execute(new SendMessage(chat.id(), "Выполняю поиск ..."));
            bot.execute(new SendMessage(chat.id(), "Введите адрес электронной почты (пример apetrov@spets.ru), или имя доменной учетной записи (пример apetrov)"));
            return "3";
        });

        SimpleScenarioStage<String, StageParams> st3 = new SimpleScenarioStage<>("3", (p) -> {
            Chat chat = p.getChat();
            TelegramBot bot = p.getBot();
            bot.execute(new SendMessage(chat.id(), "Авторизация прошла успешно. Теперь осталось подключить необходимые вам сервисы."));
            bot.execute(new SendMessage(chat.id(), "Используйте команду /myservices для управления сервисами"));
            bot.execute(new SendMessage(chat.id(), "Используйте команду /help для получения справки"));
            return null;
        });

        addStage(st1);
        addStage(st2);
        addStage(st3);

    }

    @Override
    public void save(String userId) {

    }

    @Override
    public void load(String userId) {

    }

}
