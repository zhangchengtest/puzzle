package com.elephant;

import com.cunw.tid.SnowIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PuzzleApplicationTests {

    @Autowired
    private SnowIdGenerator idGenerator;

    @Test
    void contextLoads() {
    }

    @Test
    void testtid() {
       String id =  idGenerator.getNextStr();
       System.out.println(id);
    }
}
