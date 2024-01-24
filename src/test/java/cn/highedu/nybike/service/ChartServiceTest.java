package cn.highedu.nybike.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChartServiceTest {
    @Autowired
    ChartService chartService;
    @Test
    void listHourTripCount() {
        System.out.println(chartService.listHourTripCount(2019, 9));
    }
}