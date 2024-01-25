package cn.highedu.nybike.controller;

import cn.highedu.nybike.common.exception.ServiceCode;
import cn.highedu.nybike.common.exception.ServiceException;
import cn.highedu.nybike.common.pojo.JsonResult;
import cn.highedu.nybike.po.vo.HourGenderCountDTO;
import cn.highedu.nybike.po.vo.LineVO;
import cn.highedu.nybike.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chartData")
public class ChartController {
    @Autowired
    ChartService chartService;


    @RequestMapping("/listHourGenderTripCount")
    public JsonResult<List<HourGenderCountDTO>> listHourGenderTripCount(Integer year, Integer month) {
        //表单验证
        checkYearMonth(year, month);
        //返回结果
        return JsonResult.ok(chartService.listHourGenderTripCount(year, month));
    }
    @RequestMapping("/listHourTripCount")
    public JsonResult<LineVO<Integer>> listHourTripCount(Integer year, Integer month) {
        //表单验证
        checkYearMonth(year, month);
        //返回结果
        return JsonResult.ok(chartService.listHourTripCount(year, month));
    }

    public void checkYearMonth(Integer year, Integer month) {
        if (year == null || year <2013 || year >2023) {
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"月份参数不正确");
        }
        if (month == null || month <1 || month >12){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"月份参数不正确");
        }
    }



}
