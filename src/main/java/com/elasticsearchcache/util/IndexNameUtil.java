package com.elasticsearchcache.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author lks21c
 */
public class IndexNameUtil {
    public static String getIndexName(List<String> idl) {
        String sb = "";
        for (String indexName : idl) {
            if (!StringUtils.isEmpty(sb)) {
                sb += ",";
            }

            if (indexName.contains("*")) {
                sb += indexName.substring(0, indexName.indexOf("*")) + "*";
            } else {
                sb += indexName;
            }
        }
        return sb;
    }
}
