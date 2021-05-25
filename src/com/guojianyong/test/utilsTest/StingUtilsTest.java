package com.guojianyong.test.utilsTest;

import com.guojianyong.dao.impl.simpleMBatis.utils.StingUtils;
import org.junit.Test;


public class StingUtilsTest {


    @Test
    public void getFieldSqlName() {
        String s = "userId";
        System.out.println(StingUtils.getFieldSqlName(s));



    }

    @Test
    public void getName() {
    }
}
