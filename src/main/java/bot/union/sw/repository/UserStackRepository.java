package bot.union.sw.repository;

import bot.union.sw.entities.UserStack;
import bot.union.sw.entities.UserStackPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStackRepository extends JpaRepository<UserStack, UserStackPK> {

    List<UserStack> findByChatIdOrderByNumber(Long chatId);
    long deleteByChatId(Long chatId);

}
