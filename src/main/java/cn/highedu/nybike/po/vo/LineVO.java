package cn.highedu.nybike.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineVO<T> {
    private List<String> legendData;
    private List<String> xAxisData;
    private List<SeriesBean<T>> seriesData;
}
