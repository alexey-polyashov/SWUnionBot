package bot.union.sw.repository;

import bot.union.sw.entities.AllowService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllowServicesRepository extends JpaRepository<AllowService, String> {
    Optional<AllowService> findByName(String name);
}
