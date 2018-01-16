package com.elasticsearchcache;

import com.sun.jersey.core.util.Base64;
import org.junit.Test;
import org.springframework.util.Base64Utils;

public class Base64Test {

    @Test
    public void name() {
        String str = "U29tZSBiaW5hcnkgYmxvYg==";
        byte[] b = Base64.decode(str);
        System.out.println(new String(b));
    }
    @Test
    public void name2() {
        String str = "ZXlKclpYbGZZWE5mYzNSeWFXNW5Jam9pTWpBeE9DMHdNUzB4TVZRd01Eb3dNRG93TUM0d01EQXJNRGs2TURBaUxDSnJaWGtpT2pFMU1UVTFPVFkwTURBd01EQXNJbVJ2WTE5amIzVnVkQ0k2Tnprd05ETTFNaXdpTXlJNmV5SmtiMk5mWTI5MWJuUmZaWEp5YjNKZmRYQndaWEpmWW05MWJtUWlPakFzSW5OMWJWOXZkR2hsY2w5a2IyTmZZMjkxYm5RaU9qQXNJbUoxWTJ0bGRITWlPbHQ3SW10bGVTSTZJbEJNV1NJc0ltUnZZMTlqYjNWdWRDSTZOVEF3TXpJMU9Dd2lNU0k2ZXlKMllXeDFaU0k2TlRjME5qWXdPQzR3Zlgwc2V5SnJaWGtpT2lKTlEwZ2lMQ0prYjJOZlkyOTFiblFpT2pJNU1ERXdPRFlzSWpFaU9uc2lkbUZzZFdVaU9qTXdPREV6TnpZdU1IMTlMSHNpYTJWNUlqb2lUa0pGSWl3aVpHOWpYMk52ZFc1MElqbzRMQ0l4SWpwN0luWmhiSFZsSWpveE5pNHdmWDFkZlgwPQ==";
        byte[] b = Base64.decode(str);
        System.out.println(new String(b));
    }
}
