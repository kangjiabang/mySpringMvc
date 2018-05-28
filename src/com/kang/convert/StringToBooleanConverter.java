package com.kang.convert;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author：zeqi
 * @Date: Created in 17:05 25/5/18.
 * @Description:
 */
public class StringToBooleanConverter implements  Converter<String,Boolean> {


    /**
     * 表示true的字符串集合
     */
    public static Set<String> trueSets = new HashSet<>();

    /**
     * 表示false的字符串集合
     */
    public static Set<String> falseSets = new HashSet<>();

    static {
        trueSets.add("yes");
        trueSets.add("1");
        trueSets.add("true");
        trueSets.add("on");

        falseSets.add("no");
        falseSets.add("0");
        falseSets.add("false");
        falseSets.add("off");
    }

        @Override
        public Boolean convert(String source) {
            String value = source.trim();
            if (value.equals("")) {
                return null;
            }
            value = value.toLowerCase();
            if (trueSets.contains(value)) {
                return Boolean.TRUE;
            }
            else if (falseSets.contains(value)) {
                return Boolean.FALSE;
            } else {
                throw new IllegalArgumentException("Invalid boolean value. source '" + source + "'");
            }
        }





}
