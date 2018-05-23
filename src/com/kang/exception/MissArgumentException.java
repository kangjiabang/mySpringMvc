package com.kang.exception;

/**
 * @Author：zeqi
 * @Date: Created in 16:22 23/5/18.
 * @Description:
 */
public class MissArgumentException extends RuntimeException {

    public MissArgumentException(String message) {
        super(message);
    }
}
