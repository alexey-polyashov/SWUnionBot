package bot.union.sw.common.scenariodefine;

/**
 * Менеджер сценариев. Содержит список используемых сценариев. Осуществляет регистрацию и поиск по идентификатору.
 */
public interface ScManager {
    Scenario getScenarioById(String id);
    void attach(Scenario scenario, String id);
    void detach(String id);
}
