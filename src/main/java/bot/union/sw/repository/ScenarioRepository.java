package bot.union.sw.repository;

import bot.union.sw.entyties.BotUser;
import bot.union.sw.entyties.Scenarios;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioRepository extends JpaRepository<Scenarios, Long> {

    List<Scenarios> findByBotUserOrderByOrder(BotUser botUser, Sort sort);

}
