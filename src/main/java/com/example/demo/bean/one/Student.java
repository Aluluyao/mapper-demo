package com.example.demo.bean.one;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class Student implements Serializable{

    @Id
    private Integer id;

    private String name;

    private String address;
}
