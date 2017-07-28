package com.bw.wangruixue.entity;

import lombok.Data;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by lenovo on 2017/7/20.
 */
@Data
@Entity
@NoRepositoryBean
public class SwaggerUser {

    @Id
    @GeneratedValue
    //private String id;
    private Long id;
    private String name;
    private Integer age;

    public SwaggerUser(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public SwaggerUser(){super();}
}
