package com.kang.enums;

/**
 * @Author：zeqi
 * @Date: Created in 14:10 24/5/18.
 * @Description:
 */
public enum ParamterType {

    HEADER(2,"消息头参数"),
    PARAMTER(1,"url参数");

    private  int type;

    private  String desc;


    ParamterType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
