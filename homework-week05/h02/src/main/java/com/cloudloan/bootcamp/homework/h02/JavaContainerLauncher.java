package com.cloudloan.bootcamp.homework.h02;

import com.cloudloan.bootcamp.homework.h02.base.Klass;
import com.cloudloan.bootcamp.homework.h02.base.School;
import com.cloudloan.bootcamp.homework.h02.base.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Java 方式装配 Java Bean
 *
 * @author zhaochen
 */
public class JavaContainerLauncher {

    /**
     * @param args args
     */
    public static void main(String[] args) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        final School school = (School) context.getBean("school");
        school.ding();
    }

}

/**
 * Java 方式配置类
 */
@Configuration
class Config {
    @Bean
    public School school(Klass klass, @Qualifier(value = "student1") Student student) {
        School school = new School();
        school.setKlass(klass);
        school.setStudent(student);
        return school;
    }

    @Bean
    public Klass klass(List<Student> students) {
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean
    public Student student1() {
        Student student = new Student();
        student.setId(1001);
        student.setName("学员1(java)");
        return student;
    }

    @Bean
    public Student student2() {
        Student student = new Student();
        student.setId(1002);
        student.setName("学员2(java)");
        return student;
    }
}
