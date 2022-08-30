package bot.union.sw.repository;

import bot.union.sw.entities.ScenarioModel;
import bot.union.sw.entities.UserStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStackRepository extends JpaRepository<UserStack, Long> {

    List<ScenarioModel> findByChatIdOrderByOrder(Long chatId);

}
