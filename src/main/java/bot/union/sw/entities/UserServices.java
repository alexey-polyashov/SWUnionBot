package bot.union.sw.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_services")
@Getter
@Setter
@NoArgsConstructor
public class UserServices {

    @Column(name = "bot_user")

    private BotUser botUser;

    @ManyToOne
    @JoinColumn(name = "service")
    private AllowService service;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createTime;

}
