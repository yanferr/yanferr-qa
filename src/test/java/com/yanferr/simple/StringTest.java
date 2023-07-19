package com.yanferr.simple;

import org.junit.Test;

public class StringTest {


    @Test
    public void testStr1(){
        String str = "01234567>9>>>>";
        System.out.println(str.indexOf(">>>>"));
    }
}
