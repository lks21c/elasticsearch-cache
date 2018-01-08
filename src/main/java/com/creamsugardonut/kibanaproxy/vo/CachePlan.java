package com.creamsugardonut.kibanaproxy.vo;

import org.joda.time.DateTime;

/**
 * @author lks21c
 */
public class CachePlan {
    private String cacheMode;

    private DateTime preStartDt;

    private DateTime preEndDt;

    private DateTime startDt;

    private DateTime endDt;

    private DateTime postStartDt;

    private DateTime postEndDt;

    public String getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(String cacheMode) {
        this.cacheMode = cacheMode;
    }

    public DateTime getPreStartDt() {
        return preStartDt;
    }

    public void setPreStartDt(DateTime preStartDt) {
        this.preStartDt = preStartDt;
    }

    public DateTime getPreEndDt() {
        return preEndDt;
    }

    public void setPreEndDt(DateTime preEndDt) {
        this.preEndDt = preEndDt;
    }

    public DateTime getStartDt() {
        return startDt;
    }

    public void setStartDt(DateTime startDt) {
        this.startDt = startDt;
    }

    public DateTime getEndDt() {
        return endDt;
    }

    public void setEndDt(DateTime endDt) {
        this.endDt = endDt;
    }

    public DateTime getPostStartDt() {
        return postStartDt;
    }

    public void setPostStartDt(DateTime postStartDt) {
        this.postStartDt = postStartDt;
    }

    public DateTime getPostEndDt() {
        return postEndDt;
    }

    public void setPostEndDt(DateTime postEndDt) {
        this.postEndDt = postEndDt;
    }
}
