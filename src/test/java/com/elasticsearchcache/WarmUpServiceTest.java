package com.elasticsearchcache;

import com.elasticsearchcache.warmup.WarmUpService;
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
        warmUpService.warmUpMinuteQueries();
    }
}