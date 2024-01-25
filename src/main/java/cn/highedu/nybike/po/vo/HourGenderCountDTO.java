package cn.highedu.nybike.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HourGenderCountDTO {
    private String dataHour;
    private Integer unknownCount;
    private Integer manCount;
    private Integer womanCount;
}
