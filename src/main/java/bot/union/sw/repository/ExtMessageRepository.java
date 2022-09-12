package bot.union.sw.repository;

import bot.union.sw.entities.ExtMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExtMessageRepository extends JpaRepository<ExtMessage, UUID> {

    @Query("Select count(*) From ExtMessage m Where m.ready=true AND m.passed=false AND m.numsPass<4")
    Integer getQueueSize();

    @Query("Select m From ExtMessage m Where m.ready=true AND m.passed=false AND m.numsPass<4 Order m.createTime desc  Limit :limit")
    List<ExtMessage> getQueue(@Param("limit") int limit);

    @Modifying
    @Query("Update ExtMessage m Set m.ready=true Where m.uuid=:uuid")
    void setReady(@Param("uuid") UUID uuid);

    @Modifying
    @Query("Update ExtMessage m Set m.passed=true Where m.uuid=:uuid")
    void setPassed(@Param("uuid") UUID uuid);

}
