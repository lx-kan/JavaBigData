package cn.highedu.nybike.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HourTripCountMapperTest {
    @Autowired
    HourTripCountMapper mapper;
    @Test
    public void testSelectAll() {
        mapper.selectList(null).forEach(System.out::println);
    }

}