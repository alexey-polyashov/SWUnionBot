package bot.union.sw.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_services")
@Getter
@Setter
@NoArgsConstructor
public class AllowServices {

    @Column
    String name;
    String description;

    @ManyToMany
    @JoinTable(name = "services_roles",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Roles> requiredRoles;

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
