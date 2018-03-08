package com.example.demo.service.impl;

import com.example.demo.bean.one.Student;
import com.example.demo.bean.two.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BusinessService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    public void selectAll(){
        List<Student> students = studentService.selectAll();
        log.info("students:" + students.toString());
        List<Teacher> teachers = teacherService.selectAll();
        log.info("teachers:"+teachers.toString());

    }
}
