package bot.union.sw.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewBotUserDto {
    private String email;
    private String login;
    private String domainName;
    private String password;
}
