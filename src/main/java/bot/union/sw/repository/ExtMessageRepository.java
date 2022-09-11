package bot.union.sw.repository;

import bot.union.sw.entities.ExtMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExtMessageRepository extends JpaRepository<ExtMessage, UUID> {

}
