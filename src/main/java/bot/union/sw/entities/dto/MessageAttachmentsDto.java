package bot.union.sw.entities.dto;

import bot.union.sw.entities.BotUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MessageAttachmentsDto {

    private String identifier;
    private UUID id;
    private String contentType;
    private Long fileSize;

}
