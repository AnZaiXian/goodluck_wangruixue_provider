package com.bw.wangruixue.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/7/20.
 */
@Data
public class Loggers implements Serializable{


    private String URL;
    private String HTTP_METHOD;
    private String IP;
    private String CLASS_METHOD;
    private String ARGS;


    public Loggers(String URL, String HTTP_METHOD, String IP, String CLASS_METHOD, String ARGS) {
        this.URL = URL;
        this.HTTP_METHOD = HTTP_METHOD;
        this.IP = IP;
        this.CLASS_METHOD = CLASS_METHOD;
        this.ARGS = ARGS;
    }

    public Loggers(){super();}
}
