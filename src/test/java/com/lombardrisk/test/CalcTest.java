package com.lombardrisk.test;

import com.lombardrisk.CalcDemo;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by amy sheng on 4/2/2018.
 */

public class CalcTest {
    @Test
    public void test1(){
        int actual;
        int expected;
        CalcDemo MyCalc = new CalcDemo();
        actual = MyCalc.add(5, 6);
        expected = 11;
        Assert.assertEquals(actual,expected);


    }


}
