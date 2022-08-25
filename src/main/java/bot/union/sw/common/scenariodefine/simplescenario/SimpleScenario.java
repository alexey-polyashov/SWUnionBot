package bot.union.sw.common.scenariodefine.simplescenario;

import bot.union.sw.common.scenariodefine.Scenario;
import bot.union.sw.common.scenariodefine.ScenarioStage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса Scenario
 * @param <T> - тип описывающий состояния сценария
 * @param <P> - тип описывающий параметры этапов сценария
 */
public abstract class SimpleScenario<T, P> implements Scenario<T, P> {

    String scenarioId;

    List<ScenarioStage<T, P>> stageChain;
    ScenarioStage<T, P> currentStage;
    boolean done;
    boolean started;

    public SimpleScenario() {
        this.stageChain = new ArrayList<>();
        init();
    }

    /**
     * Инициализация сценария. Возможно определение этапов сценария
     */
    public abstract void init();

    @Override
    public void goToEnd() {
        this.currentStage = null;
        this.done = true;
    }

    @Override
    public void goToStart() {
        start();
    }

    @Override
    public void doWork(P stageParams) {
        Optional<T> nextStage = Optional.empty();
        if(currentStage!=null) {
            nextStage = currentStage.getNextStage(stageParams);
        }
        nextStage.ifPresentOrElse(
                (v)-> {
                    if (!v.equals(currentStage.getIdentifier())) {
                        goToStage(v);
                        checkEndState();
                    }
                },
                this::goToEnd
        );
    }

    @Override
    public void start() {
        this.started = true;
        if (stageChain.size()>0) {
            this.currentStage = stageChain.get(0);
        }
        checkEndState();
    }

    @Override
    public ScenarioStage<T, P> getCurrentStage() {
        return currentStage;
    }

    @Override
    public void goToStage(T stageId) {
        getStage(stageId).ifPresent((v)-> {
                                            this.currentStage = v;
                                            checkEndState();
        });
    }

    @Override
    public void addStage(ScenarioStage<T, P> scenarioStage) {
        stageChain.add(scenarioStage);
    }

    @Override
    public void setScenarioStage(T identifier, ScenarioStage<T, P> scenarioStage) {

    }

    @Override
    public void delScenarioStage(T identifier) {

    }

    @Override
    public List<ScenarioStage<T, P>> getStages() {
        return null;
    }

    @Override
    public Optional<ScenarioStage<T, P>> getStage(T stageId) {
        Optional<ScenarioStage<T, P>> result = Optional.empty();
        for (ScenarioStage<T, P> stage: stageChain) {
            if(stage.getIdentifier()==stageId){
                result  = Optional.of(stage);
            }
        }
        return result;
    }

    @Override
    public String getId() {
        return scenarioId;
    }

    @Override
    public void setId(String id) {
        this.scenarioId = id;
    }

    @Override
    public void save(String userId) {

    }

    @Override
    public void load(String userId) {

    }

    protected void checkEndState(){
        if (stageChain.size()>0) {
            if (this.currentStage == null){
                this.done = true;
            }
        }else{
            this.done = true;
        }
    }
}
