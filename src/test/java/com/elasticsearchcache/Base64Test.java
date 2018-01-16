package com.elasticsearchcache;

import com.sun.jersey.core.util.Base64;
import org.junit.Test;

public class Base64Test {

    @Test
    public void name() {
        String str = "U29tZSBiaW5hcnkgYmxvYg==";
        byte[] b = Base64.decode(str);
        System.out.println(new String(b));
    }
}
