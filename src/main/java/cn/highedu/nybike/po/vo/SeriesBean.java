package cn.highedu.nybike.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesBean<T> {
    private String name;
    private String type;
    private String stack;
    private List<T> data;
}
