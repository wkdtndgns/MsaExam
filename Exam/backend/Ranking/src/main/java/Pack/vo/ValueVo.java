package Pack.vo;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ValueVo {
    List<Long> exam;
    Long key;
}
