package com.cloudloan.bootcamp.homework.h02.config;

import com.cloudloan.bootcamp.homework.h02.base.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class AnnoConfig {

    @Bean
    public Student student1() {
        Student student = new Student();
        student.setId(1001);
        student.setName("学员1(annotation)");
        return student;
    }

    @Bean
    public Student student2() {
        Student student = new Student();
        student.setId(1002);
        student.setName("学员2(annotation)");
        return student;
    }

}
