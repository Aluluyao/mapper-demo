package com.example.demo.service;

import com.example.demo.utils.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class MapperService<T> {

    @Autowired
    protected MyMapper<T> myMapper;

    public List<T> selectAll(){
        return myMapper.selectAll();
    }
}
