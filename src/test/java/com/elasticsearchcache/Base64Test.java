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

    @Test
    public void name2() {
        String str = "eyJrZXlfYXNfc3RyaW5nIjoiMjAxOC0wMS0yMVQwMDowMDowMC4wMDArMDk6MDAiLCJrZXkiOjE1MTY0NjA0MDAwMDAsImRvY19jb3VudCI6ODMwNDMzOCwiMiI6eyJkb2NfY291bnRfZXJyb3JfdXBwZXJfYm91bmQiOi0xLCJzdW1fb3RoZXJfZG9jX2NvdW50Ijo0NTkxODU1LCJidWNrZXRzIjpbeyJrZXkiOiLslajrspQiLCJkb2NfY291bnQiOjM3MDQ0NzgsIjEiOnsidmFsdWUiOjUzNjgyMTMuMH0sIjMiOnsiZG9jX2NvdW50X2Vycm9yX3VwcGVyX2JvdW5kIjotMSwic3VtX290aGVyX2RvY19jb3VudCI6MzE3MjQ5MCwiYnVja2V0cyI6W3sia2V5Ijoi7KO87J246rO1IiwiZG9jX2NvdW50IjoxMDE4NjQsIjEiOnsidmFsdWUiOjI0OTcwNi4wfX0seyJrZXkiOiJIeXBlcnJlYWwiLCJkb2NfY291bnQiOjc2NTgwLCIxIjp7InZhbHVlIjoxNTY5MzguMH19LHsia2V5IjoiVEhFIFVOSSsgRyBTVEVQIDEiLCJkb2NfY291bnQiOjY5MjcxLCIxIjp7InZhbHVlIjoxMzk4OTMuMH19LHsia2V5Ijoi6re464Kg7LKY65+8IiwiZG9jX2NvdW50Ijo1ODE4NCwiMSI6eyJ2YWx1ZSI6MTAyNzgwLjB9fSx7ImtleSI6Iu2ZlOycoOq4sCBPU1QgUGFydC40IiwiZG9jX2NvdW50Ijo1NzI1MSwiMSI6eyJ2YWx1ZSI6OTcxMzIuMH19LHsia2V5Ijoi64+I6r2DIE9TVCBQYXJ0LjUiLCJkb2NfY291bnQiOjQ0ODE4LCIxIjp7InZhbHVlIjo4MTczNS4wfX0seyJrZXkiOiJUT1AgU0VFRCIsImRvY19jb3VudCI6MTkxMjgsIjEiOnsidmFsdWUiOjcwODg2LjB9fSx7ImtleSI6IkdSRUFUISIsImRvY19jb3VudCI6MjkzNTgsIjEiOnsidmFsdWUiOjY3MTc4LjB9fSx7ImtleSI6IjEwMTMxMTcwIiwiZG9jX2NvdW50IjozOTg3NiwiMSI6eyJ2YWx1ZSI6NjU2NzguMH19LHsia2V5IjoiT2Zmc2V0IiwiZG9jX2NvdW50IjozNTY1OCwiMSI6eyJ2YWx1ZSI6NTcyODYuMH19XX19XX19";
        byte[] b = Base64.decode(str);
        System.out.println(new String(b));
    }

    @Test
    public void name3() {
        String str = "eyJrZXlfYXNfc3RyaW5nIjoiMjAxOC0wNi0xMVQwMjo1NzowMC4wMDBaIiwia2V5IjoxNTI4Njg1ODIwMDAwLCJkb2NfY291bnQiOjI2NjY3LCJjYXJkaW5hbGl0eSNjYXJkaW5hbGl0eSI6eyJ2YWx1ZSI6MTU0Mjl9fQ==";
        byte[] b = Base64.decode(str);
        System.out.println(new String(b));
    }
}
