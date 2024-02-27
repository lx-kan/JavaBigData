package cn.highedu.nybike.po.entity;

import lombok.Data;

@Data
public class HourStationTripCount {
    private String startdate;
    private Integer hour;
    private Integer stationId;
    private String stationName;
    private Integer count;
    private String type; // start 或 end
}
