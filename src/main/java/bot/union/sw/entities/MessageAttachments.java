package bot.union.sw.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "message_attachments")
@Getter
@Setter
@NoArgsConstructor
public class MessageAttachments {

    @ManyToOne
    @JoinColumn(name = "message")
    private BotUser message;
    @Column
    private String identifier;
    @Lob
    @Column(name = "data", columnDefinition="BLOB")
    private Byte[] data;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createTime;
}
