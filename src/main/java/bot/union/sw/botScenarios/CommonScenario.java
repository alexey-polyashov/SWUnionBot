package bot.union.sw.botScenarios;

import bot.union.sw.common.scenariodefine.simplescenario.SimpleScenario;
import bot.union.sw.services.BotService;
import bot.union.sw.services.ScenarioService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommonScenario<T, P> extends SimpleScenario<T, P> {

    private ScenarioService scenarioService;
    private BotService botService;

    public static final String str_main_menu_authorization = "Авторизоваться";
    public static final String str_main_menu_authorization_cmd = "/auth";
    public static final String str_main_menu_help = "Помощь";
    public static final String str_main_menu_cmd = "/help";
    public static final String str_main_menu_settings = "Настройки";
    public static final String str_main_menu_settings_cmd = "/settings";
    public static final String str_main_menu_new_querry = "Новый запрос";
    public static final String str_main_menu_new_querry_cmd = "/newquerry";
    public static final String str_main_menu_my_querries = "Мои запросы";
    public static final String str_main_menu_my_querries_cmd = "/myquerries";

}
