package com.elasticsearchcache.warmup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WarmUpService {
    private static final Logger logger = LogManager.getLogger(WarmUpService.class);

    @Scheduled(fixedDelay = 60000)
    public void warmUpMinuteQueries() {
        logger.info("warmup");
    }
}
