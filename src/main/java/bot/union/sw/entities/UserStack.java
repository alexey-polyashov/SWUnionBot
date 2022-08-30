package bot.union.sw.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_stack")
@Getter
@Setter
@NoArgsConstructor
public class UserStack {

    @Id
    private Long chatId;
    @Id
    private Integer order;
    private String scenarioId;

}
