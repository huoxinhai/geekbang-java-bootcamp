package com.cloudloan.bootcamp.homework.starter.base;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kimmking
 */
@Component(value = "school")
@Data
public class School implements ISchool {

    @Resource
    private Klass klass;

    @Resource(name = "student1")
    private Student student;

    @Override
    public void ding() {
        System.out.println("klass have " + this.klass.getStudents().size() + " students and one is " + this.student);
    }

}
