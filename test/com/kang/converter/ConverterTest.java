package com.kang.converter;

import com.kang.convert.Converter;
import com.kang.convert.StringToBooleanConverter;
import com.kang.convert.StringToCharacterConverter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author：zeqi
 * @Date: Created in 12:23 27/5/18.
 * @Description:
 */
public class ConverterTest {

    @Test
    public void testStringToBooleanConverter() {
        Converter converter = new StringToBooleanConverter();
        Assert.assertEquals(converter.convert("true"),true);
        Assert.assertEquals(converter.convert("on"),true);
        Assert.assertEquals(converter.convert("1"),true);
        Assert.assertEquals(converter.convert("false"),false);
        Assert.assertEquals(converter.convert("off"),false);
        Assert.assertEquals(converter.convert("0"),false);

        //test exception
        try {
            converter.convert("unkown");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testStringToCharacterConverter() {
        StringToCharacterConverter converter = new StringToCharacterConverter();
        System.out.println(Character.MAX_VALUE + 0);
        Assert.assertEquals( converter.convert("t") <= Character.MAX_VALUE && Character.MIN_VALUE <= converter.convert("t"),true);

        //test exception
        try {
            converter.convert("unkown");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testClassHierachy() {
        Integer number = 5;
        Class cls = number.getClass();
        while(cls.getSuperclass() != null) {
            System.out.println(cls.getSuperclass());
            cls = cls.getSuperclass();
        }

    }
}
