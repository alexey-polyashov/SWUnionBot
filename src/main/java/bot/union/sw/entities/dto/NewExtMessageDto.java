package bot.union.sw.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class NewExtMessageDto {

    private String textMessage;
    private String serviceName;
    private String userIdentifier;//логин, email

}
