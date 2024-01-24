package cn.highedu.nybike.po.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("t_hour_trip_count")
public class HourTripCount {
    private String startdata;
    private Integer hour;
    private Integer count;

}
