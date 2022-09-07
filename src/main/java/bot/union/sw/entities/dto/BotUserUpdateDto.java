package bot.union.sw.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BotUserUpdateDto {

    private Long id;
    private String email;
    private String login;
    private String domainName;

}
