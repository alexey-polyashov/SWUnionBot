package bot.union.sw.entyties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bot_users")
@Getter
@Setter
@NoArgsConstructor
public class BotUser {

    @Column(name="chat_id")
    Long chatId;
    @Column(name="email")
    String email;
    @Column(name="login")
    String login;
    @Column(name="name")
    String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "marked")
    @ColumnDefault("false")
    private Boolean marked = false;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createTime;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updateTime;

}
