package cn.highedu.nybike.po.entity;

import lombok.Data;

@Data
public class HourStationTupleInfo {
    private String startdate;
    private Integer hour;
    private Integer startStationId;
    private Integer endStationId;

}
