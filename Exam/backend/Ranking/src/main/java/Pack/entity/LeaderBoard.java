package Pack.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="LeaderBoard")
@Getter
@Setter
public class LeaderBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true)
    private String name;

    @Column(nullable = true)
    @ColumnDefault("0")
    private Long score;

    public LeaderBoard(String name, Long score) {
        this.name = name;
        this.score = score;
    }
}
