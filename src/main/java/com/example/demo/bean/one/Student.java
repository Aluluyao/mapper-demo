package com.example.demo.bean.one;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable{

    private Integer id;

    private String name;

    private String address;
}
