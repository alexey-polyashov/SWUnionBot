package bot.union.sw.services;

import bot.union.sw.common.scenariodefine.Scenario;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScenarioService {

    Map<String, String> storage = new HashMap<>();

    public Scenario restoreScenario(String UserId, String scenarioId){
        String json = storage.get("" + UserId + "#" + scenarioId);
        return null;
    }

    public void saveScenario(Scenario sc, String UserId){
        String id = sc.getId();
    }

}
