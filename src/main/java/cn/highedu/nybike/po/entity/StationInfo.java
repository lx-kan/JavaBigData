package cn.highedu.nybike.po.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_station_info")
public class StationInfo {
    private Integer stationId;
    private String stationName;
    private Double stationLatitude;
    private Double stationLongitude;
}
