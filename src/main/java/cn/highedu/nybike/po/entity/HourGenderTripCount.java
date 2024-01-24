package cn.highedu.nybike.po.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("t_hour_gender_trip_count")
public class HourGenderTripCount {
    private String startdata;
    private Integer hour;
    private Integer gender ; //0:未知 1:男 2:女
    private Integer count;

}
