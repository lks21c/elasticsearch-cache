package com.elasticsearchcache;

import org.junit.Test;

public class SplitTest {

    @Test
    public void name() {
        String url = "http://mpalyes11:19200/mel_com_private_join_profile*/info/_search?typed_keys";
        System.out.println(url.split("/")[3]);
    }
}
