package com.kang.convert;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author：zeqi
 * @Date: Created in 17:05 25/5/18.
 * @Description:
 */
public class StringToCharacterConverter implements  Converter<String,Character> {

        @Override
        public Character convert(String source) {
            String value = source.trim();
            if (value.equals("")) {
                return null;
            }
            if (source.length() > 1) {
                throw new IllegalArgumentException("source '" + source + "' can not be converted to character.");
            }
            return source.charAt(0);
        }

}
