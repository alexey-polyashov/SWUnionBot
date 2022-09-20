package bot.union.sw.repository;

import bot.union.sw.entities.AllowService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllowServicesRepository extends JpaRepository<AllowService, String> {
    Optional<AllowService> findByName(String name);
    List<AllowService> findByMarked(boolean b);

    @Query(value="Select aServ.* " +
            " From bot_users As bu" +
            "   Inner Join user_roles As ur On ur.user_id = bu.id " +
            "   Inner join services_roles As sr On sr.role_id = ur.role_id " +
            "   Inner Join allow_services AS aServ On sr.service_name = aServ.name " +
            " Where bu.chat_id=:chatId", nativeQuery = true)
    List<AllowService> findAllowServicesForUser(@Param("chatId") Long chatId);
}
