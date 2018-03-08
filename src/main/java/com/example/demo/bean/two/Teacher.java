package com.example.demo.bean.two;

import lombok.Data;

import java.io.Serializable;

@Data
public class Teacher implements Serializable{

    private Integer id;

    private String name;

    private Integer age;
}
