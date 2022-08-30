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

}
