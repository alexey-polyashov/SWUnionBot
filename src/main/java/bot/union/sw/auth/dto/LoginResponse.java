package bot.union.sw.auth.dto;

import bot.union.sw.entities.dto.BotUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginResponse {
    String jwt;
    BotUserDto botUser;
}
