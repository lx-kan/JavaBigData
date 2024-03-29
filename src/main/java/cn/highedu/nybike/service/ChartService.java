package cn.highedu.nybike.service;

import cn.highedu.nybike.common.exception.ServiceCode;
import cn.highedu.nybike.common.exception.ServiceException;
import cn.highedu.nybike.mapper.HourGenderTripCountMapper;
import cn.highedu.nybike.mapper.HourTripCountMapper;
import cn.highedu.nybike.po.entity.HourGenderTripCount;
import cn.highedu.nybike.po.entity.HourTripCount;
import cn.highedu.nybike.po.vo.HourGenderCountDTO;
import cn.highedu.nybike.po.vo.LineVO;
import cn.highedu.nybike.po.vo.SeriesBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {
    @Autowired
    HourTripCountMapper hourTripCountMapper;
    @Autowired
    HourGenderTripCountMapper hourGenderTripCountMapper;
    public List<HourGenderCountDTO> listHourGenderTripCount(Integer year, Integer month){
        //构建查询对象
        QueryWrapper<HourGenderTripCount> wrapper = new QueryWrapper<>();
        String yearMonth = year + "-" + (month<10 ? "0" + month : month);
        //wrapper.likeRight("startdata", yearMonth);
        wrapper.lambda().likeRight(HourGenderTripCount::getStartdata, yearMonth);
        //添加排序条件
        wrapper.lambda().orderByAsc(HourGenderTripCount::getStartdata).orderByAsc(HourGenderTripCount::getHour);
        //执行查询
        List<HourGenderTripCount> hourGenderTripCounts = hourGenderTripCountMapper.selectList(wrapper);
        if (hourGenderTripCounts.size()==0){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"没有数据");
        }
        //数据封装
        String dataHour = null;
        HourGenderCountDTO tempDTO = null;
        List<HourGenderCountDTO> list = new ArrayList<>(24*31);
        for (HourGenderTripCount tripCount : hourGenderTripCounts){
            // yyyy-MM-dd,integer -> yyyy/MM/dd HH:00
            String startData = tripCount.getStartdata().replace("-", "/");
            startData += " " + (tripCount.getHour() < 10 ? "0" + tripCount.getHour() : tripCount.getHour());
            startData += ":00";
            if(!startData.equals(dataHour)){
                //新一个小时的数据
                //保存上一个小时的数据
                if (tempDTO != null){
                    list.add(tempDTO);
                }
                //创建新一个小时的对象
                tempDTO = new HourGenderCountDTO();
                tempDTO.setDataHour(startData);
                //更新标记
                dataHour = startData;
            }
                //保存count
            switch (tripCount.getGender()){
                case 1:
                    tempDTO.setManCount(tripCount.getCount());
                    break;
                case 2:
                    tempDTO.setWomanCount(tripCount.getCount());
                    break;
                default:
                    tempDTO.setUnknownCount(tripCount.getCount());
            }
        }
        //保存最后一个小时的数据
        list.add(tempDTO);
        //返回结果
        return list;
    }

    public LineVO<Integer> listHourTripCount(Integer year, Integer month){
        // 构建一个基于年份和月份的查数据的Mapper
        QueryWrapper<HourTripCount> wrapper = new QueryWrapper<>();
        String yearMonth = year + "-" + (month<10 ? "0" + month : month);
        //wrapper.likeRight("startdata", yearMonth);
        wrapper.lambda().likeRight(HourTripCount::getStartdata, yearMonth);
        //添加排序条件
        wrapper.lambda().orderByAsc(HourTripCount::getStartdata).orderByAsc(HourTripCount::getHour);
        //查询数据
        List<HourTripCount> hourTripCounts = hourTripCountMapper.selectList(wrapper);
        //判断是否有数据
        if (hourTripCounts.size()==0){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"没有数据");
        }
        //封装数据
        LineVO<Integer> lineVO = new LineVO<>();
        //添加X轴数据
        List<String> xAxisData = new ArrayList<>();
        for (int i = 0; i<=23; i++){
            xAxisData.add(String.valueOf(i));
        }
        lineVO.setXAxisData(xAxisData);
        //添加Series数据
        List<SeriesBean<Integer>> seriesData = new ArrayList<>(31);
        String startData = null;
        SeriesBean<Integer> seriesBean = null;
//        for (HourTripCount tripCount : hourTripCounts){
//            if (tripCount.getStartdata().equals(startData)){ //同一天
//                seriesBean.getData().add(tripCount.getCount());
//            }else { //新一天
//                startData = tripCount.getStartdata(); //更新临时变量
//                seriesBean = new SeriesBean<>(); //新建一个SeriesBean
//                seriesBean.setName(startData);
//                seriesBean.setType("line");
//                seriesBean.setStack("Total");
//                seriesBean.setData(new ArrayList<Integer>(24));
//                seriesBean.getData().add(tripCount.getCount());
//
//            }
//        }
        for (HourTripCount tripCount : hourTripCounts){
            if (!tripCount.getStartdata().equals(startData)){ //同一天
                //保存前一天的数据
                if (startData != null){
                    seriesData.add(seriesBean); //添加到SeriesData中
                }
                //初始化新一天的数据
                startData = tripCount.getStartdata(); //更新临时变量
                seriesBean = new SeriesBean<>(); //新建一个SeriesBean
                seriesBean.setName(startData);
                seriesBean.setType("line");
                seriesBean.setStack("Total");
                seriesBean.setData(new ArrayList<Integer>(24));
            }
            //保存当天的数据
            seriesBean.getData().add(tripCount.getCount());
        }
        //添加最后一天的数据
        seriesData.add(seriesBean);
        lineVO.setSeriesData(seriesData);
        //添加legendData数据
        List<String> legendData = new ArrayList<>(31);
        for (SeriesBean<Integer> item : seriesData){
            legendData.add(item.getName());
        }
        lineVO.setLegendData(legendData);
        //返回数据
        return lineVO;
    }
    
}
