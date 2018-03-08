package com.example.demo.bean.two;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class Teacher implements Serializable{

    @Id
    private Integer id;

    private String name;

    private Integer age;
}
