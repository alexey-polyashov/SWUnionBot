package bot.union.sw.repository;

import bot.union.sw.botScenarios.StageParams;
import bot.union.sw.entities.ScenarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScenarioRepository extends JpaRepository<ScenarioModel, Long> {

    Optional<ScenarioModel> findByChatIdAndIdentifier(Long chatId, String identifier);
    List<ScenarioModel> findByChatId(Long chatId);

    long deleteByChatId(Long id);
}
