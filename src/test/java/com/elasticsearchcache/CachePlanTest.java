package com.elasticsearchcache;

import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.util.DateUtil;
import com.elasticsearchcache.vo.CachePlan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CachePlanTest {
    private static final Logger logger = LogManager.getLogger(CachePlanTest.class);

    @Autowired
    CacheService cacheService;

    @Test
    public void testExactRangeDay() {
        DateTime startDt = DateUtil.getDateTime(2018, 1, 1, 0, 0);
        DateTime endDt = DateUtil.getDateTime(2018, 1, 7, 0, 0).minusMillis(1);
        CachePlan plan = cacheService.checkCachePlan("1d", startDt, endDt);
        assertNull(plan.getPreStartDt());
        assertNull(plan.getPreEndDt());
        assertEquals(startDt, plan.getStartDt());
        assertEquals(endDt, plan.getEndDt());
        assertNull(plan.getPostStartDt());
        assertNull(plan.getPostStartDt());

        logger.info("after cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.info("after cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.info("after cachePlan getStartDt = " + plan.getStartDt());
        logger.info("after cachePlan getEndDt = " + plan.getEndDt());
        logger.info("after cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.info("after cachePlan getPostEndDt = " + plan.getPostStartDt());
    }

    @Test
    public void testPreRangeDay() {
        DateTime startDt = DateUtil.getDateTime(2018, 1, 1, 13, 0);
        DateTime endDt = DateUtil.getDateTime(2018, 1, 7, 0, 0).minusMillis(1);
        CachePlan plan = cacheService.checkCachePlan("1d", startDt, endDt);

        assertEquals(startDt, plan.getPreStartDt());
        assertEquals(DateUtil.getDateTime(2018, 1, 2, 0, 0).minusMillis(1), plan.getPreEndDt());
        assertEquals(DateUtil.getDateTime(2018, 1, 2, 0, 0), plan.getStartDt());
        assertEquals(endDt, plan.getEndDt());
        assertNull(plan.getPostStartDt());
        assertNull(plan.getPostStartDt());

        logger.info("after cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.info("after cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.info("after cachePlan getStartDt = " + plan.getStartDt());
        logger.info("after cachePlan getEndDt = " + plan.getEndDt());
        logger.info("after cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.info("after cachePlan getPostEndDt = " + plan.getPostEndDt());
    }

    @Test
    public void testAfterRangeDay() {
        DateTime startDt = DateUtil.getDateTime(2018, 1, 1, 0, 0);
        DateTime endDt = DateUtil.getDateTime(2018, 1, 7, 0, 0).minusMillis(2000);
        CachePlan plan = cacheService.checkCachePlan("1d", startDt, endDt);

        assertNull(plan.getPreStartDt());
        assertNull(plan.getPreEndDt());
        assertEquals(startDt, plan.getStartDt());
        assertEquals(DateUtil.getDateTime(2018, 1, 6, 0, 0).minusMillis(1),plan.getEndDt());
        assertEquals(DateUtil.getDateTime(2018, 1, 6, 0, 0),plan.getPostStartDt());
        assertEquals(endDt, plan.getPostEndDt());

        logger.info("after cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.info("after cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.info("after cachePlan getStartDt = " + plan.getStartDt());
        logger.info("after cachePlan getEndDt = " + plan.getEndDt());
        logger.info("after cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.info("after cachePlan getPostEndDt = " + plan.getPostEndDt());
    }
}
