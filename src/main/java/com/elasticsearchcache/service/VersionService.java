package com.elasticsearchcache.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lks21c
 */
@Service
public class VersionService {
    private static final Logger logger = LogManager.getLogger(VersionService.class);

    @Value("${target.es.version}")
    private String version;

    public int getMajorVersion() {
        return Integer.parseInt(version.split("\\.")[0]);
    }
}
