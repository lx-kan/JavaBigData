package cn.highedu.nybike.mapper;

import cn.highedu.nybike.po.entity.HourStationTripCount;
import cn.highedu.nybike.po.entity.HourStationTupleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HourStationTripMapper {

    /**
     * 按月查询每天每小时起始站点出行数量
     * @param yearMonth yyyy-MM
     * @return
     */
    List<HourStationTripCount> listStartStationTripCount(String yearMonth);

    /**
     * 按月查询每天每小时终止站点出行数量
     * @param yearMonth yyyy-MM
     * @return
     */
    List<HourStationTripCount> listEndStationTripCount(String yearMonth);

    /**
     * 按月查询每天每小时起始站点和终止站点的经纬度
     * @param yearMonth yyyy-MM
     * @return
     */
    List<HourStationTupleInfo> listHourStationTupleInfo(String yearMonth);

}
