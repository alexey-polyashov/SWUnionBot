package bot.union.sw.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BotUserSimpleDto {

    private Long id;
    private String name;
    private String email;
    private Boolean marked;
    private Boolean blocked;


}
