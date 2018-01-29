package com.elasticsearchcache;

import com.elasticsearchcache.warmup.WarmUpService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WarmUpServiceTest {
    @Autowired
    WarmUpService warmUpService;

    @Test
    public void testWarmUpMinuteQueries() {
        // minute
        warmUpService.warmUpMinuteQueries();
    }

    @Test
    public void testWarmUpDayQueries() {
        //day
        warmUpService.warmUpDayQueries();
    }

    @Test
    public void testWarmUpHourQueries() {
        //Hour
        warmUpService.warmUpHourQueries();
    }
}
