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
@Table(name = "scenarios")
@Getter
@Setter
@NoArgsConstructor
public class Scenarios {

    @ManyToOne
    BotUser botUser;
    @Column(name="order")
    Integer order;
    @Column(name="current")
    String identifier;
    @Column(name = "current_stage")
    String currentStage;
    Boolean started;
    Boolean done;

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
