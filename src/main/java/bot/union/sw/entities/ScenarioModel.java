package bot.union.sw.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "scenario")
@Getter
@Setter
@NoArgsConstructor
public class ScenarioModel {

    @Id
    private Long chatId;
    @Id
    private String identifier;

    @Column(name = "data")
    private String data;
}
