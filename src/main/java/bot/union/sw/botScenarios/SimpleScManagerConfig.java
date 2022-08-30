package bot.union.sw.botScenarios;

import bot.union.sw.common.scenariodefine.simplescenario.SimpleScManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SimpleScManagerConfig extends SimpleScManager<String, StageParams> {

    @PostConstruct
    private void init(){
        this.attach(NewUserConnectedScenario.class, "NewUserConnectedScenario");
    }

}
