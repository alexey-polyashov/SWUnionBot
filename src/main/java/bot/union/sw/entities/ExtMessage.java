package bot.union.sw.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "external_messages")
@Getter
@Setter
@NoArgsConstructor
public class ExtMessage {

    @Column
    private String textMessage;
    @ManyToOne
    @JoinColumn(name = "service")
    private AllowServices service;
    @ManyToOne
    @JoinColumn(name = "bot_user")
    private BotUser botUser;

    @OneToMany(cascade=ALL, mappedBy="message")
    List<MessageAttachments> attachments;

    @Column
    private Boolean passed;
    @Column
    private Integer numsPass;
    @Column
    private Boolean error;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createTime;

}
