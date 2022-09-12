package bot.union.sw.repository;

import bot.union.sw.entities.AllowService;
import bot.union.sw.entities.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {

    Optional<BotUser> findByEmail(String identifier);
    Optional<BotUser> findByDomainName(String identifier);
    Optional<BotUser> findByChatId(Long chatId);

    List<BotUser> findByUserServices(AllowService service);

}
