package bot.union.sw.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_stack")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserStack {

    @Id
    private Long chatId;
    @Id
    private Integer number;
    private String scenarioId;

}
