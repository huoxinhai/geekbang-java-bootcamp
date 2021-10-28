package com.cloudloan.bootcamp.homework.h02.base;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kimmking
 */
@Component(value = "school")
@Data
public class School implements ISchool {

    @Autowired
    private Klass klass;

    @Resource(name = "student1")
    private Student student;

    @Override
    public void ding() {
        System.out.println("klass have " + this.klass.getStudents().size() + " students and one is " + this.student);
    }

}
