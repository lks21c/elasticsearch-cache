package com.elasticsearchcache.service;

import com.elasticsearchcache.conts.CacheMode;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.util.PeriodUtil;
import com.elasticsearchcache.vo.CachePlan;
import com.elasticsearchcache.vo.DateHistogramBucket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lks21c
 */
@Service
public class CachePlanService {
    private static final Logger logger = LogManager.getLogger(CacheService.class);

    @Value("${esc.cache.lastendtime.ts}")
    private long lastEndTimeTs;

    public boolean checkCacheable(String interval, DateTime targetDt, DateTime startDt, DateTime endDt) {
        CachePlan plan = checkCachePlan(interval, startDt, endDt);
        if (interval != null) {
            if (targetDt.getMillis() >= plan.getStartDt().getMillis()
                    && targetDt.getMillis() < plan.getEndDt().minus(lastEndTimeTs).getMillis()) {
                return true;
            }
        }
        return false;
    }

    public CachePlan checkCachePlan(String interval, DateTime startDt, DateTime endDt) {
        CachePlan cachePlan = new CachePlan();
        if (interval != null) {
            int periodUnit = PeriodUtil.getPeriodUnit(interval);
            logger.info("periodUnit = " + periodUnit);
            if (PeriodUtil.getRestMills(startDt, periodUnit) == 0) { //pre range doesn't exist
                cachePlan.setStartDt(startDt);
            } else { //pre range exists
                DateTime newStartDt = PeriodUtil.getNewStartDt(startDt, periodUnit);
                DateTime preStartDt = startDt;
                DateTime preEndDt = newStartDt.minusMillis(1);
                cachePlan.setPreStartDt(preStartDt);
                cachePlan.setPreEndDt(preEndDt);
                cachePlan.setStartDt(newStartDt);
            }

            if (PeriodUtil.getRestMills(endDt, periodUnit) == periodUnit - 1) { //end range doesn't exist
                cachePlan.setEndDt(endDt);
            } else { //end range exists
                DateTime postEndDt = endDt;
                DateTime postStartDt = endDt.minus(PeriodUtil.getRestMills(endDt, periodUnit));
                DateTime newEndDt = postStartDt.minusMillis(1);
                cachePlan.setPostStartDt(postStartDt);
                cachePlan.setPostEndDt(postEndDt);
                cachePlan.setEndDt(newEndDt);
            }
        }
        return cachePlan;
    }

    public CachePlan checkCacheMode(String interval, CachePlan plan, List<DateHistogramBucket> dhbList) {
        logger.info("checkCacheMode = " + JsonUtil.convertAsString(plan));
        if (interval != null) {
            int intervalNum = PeriodUtil.parseIntervalNum(interval);
            int periodUnit = PeriodUtil.getPeriodUnit(interval);

            if (periodUnit == -1) {
                plan.setCacheMode(CacheMode.NOCACHE);
                return plan;
            }

            int periodBetween = PeriodUtil.periodBetween(plan.getStartDt(), plan.getEndDt(), (intervalNum * periodUnit));
            logger.info("periodBetween = " + periodBetween);

            if (periodBetween + 1 == dhbList.size()
                    && plan.getPreStartDt() == null
                    && plan.getPreEndDt() == null
                    && plan.getPostStartDt() == null
                    && plan.getPostEndDt() == null) {
                plan.setCacheMode(CacheMode.ALL);
                return plan;
            } else if (dhbList.size() > 0) {
                DateTime preDateTime = null;
                boolean isSuccessive = false;
                for (DateHistogramBucket dhb : dhbList) {
                    if (preDateTime != null && PeriodUtil.periodBetween(preDateTime, dhb.getDate(), periodUnit) == 1) {
                        isSuccessive = true;
                    } else {
                        isSuccessive = false;
                    }
                    preDateTime = dhb.getDate();
                }

                logger.info("isSuccessive = " + isSuccessive);
                if (isSuccessive || dhbList.size() == 1) {
                    if (PeriodUtil.periodBetween(dhbList.get(0).getDate(), plan.getStartDt(), periodUnit) != 0) {
                        plan.setPreStartDt(plan.getStartDt());
                        plan.setStartDt(dhbList.get(0).getDate());
                        plan.setPreEndDt(dhbList.get(0).getDate().minusMillis(1));
                    }

                    if (PeriodUtil.periodBetween(dhbList.get(dhbList.size() - 1).getDate(), plan.getEndDt(), periodUnit) != 0) {
                        plan.setPostStartDt(dhbList.get(dhbList.size() - 1).getDate().plus(periodUnit));
                        plan.setPostEndDt(plan.getEndDt());
                        plan.setEndDt(dhbList.get(dhbList.size() - 1).getDate().plus(periodUnit).minusMillis(1));
                    }
                    plan.setCacheMode(CacheMode.PARTIAL);
                    return plan;
                }
            }
        }
        plan.setCacheMode(CacheMode.NOCACHE);
        return plan;
    }
}
