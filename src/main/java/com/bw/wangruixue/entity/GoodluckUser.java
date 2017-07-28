package com.bw.wangruixue.entity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by WangRuiXue on 2017/7/28.
 * 用户注册-- 用户名，密码（加密存储），年龄（下拉框），性别（单选框），头像（上传头像图片），爱好（复选框）
 */
@Data
@Entity
public class GoodluckUser implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String pwd;
    private Integer age;
    private String sex;
    private String path;
    private String hobby;
    //private File imgfile;


}
