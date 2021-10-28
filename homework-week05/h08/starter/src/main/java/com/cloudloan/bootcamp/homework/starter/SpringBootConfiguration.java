package com.cloudloan.bootcamp.homework.starter;

import com.cloudloan.bootcamp.homework.starter.base.Student;
import com.cloudloan.bootcamp.homework.starter.props.HomeworkProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 自定义 spring boot starter 配置文件
 *
 * @author Zhaochen
 */
@Configuration
@ComponentScan("com.cloudloan.bootcamp.homework.starter.base")
@EnableConfigurationProperties(HomeworkProperties.class)
@ConditionalOnProperty(prefix = "bootcamp-homework-starter", name = "enabled", havingValue = "true", matchIfMissing = false)
public class SpringBootConfiguration {

    @Resource
    private HomeworkProperties properties;

    @Bean
    public Student student1() {
        System.out.println("build student 1");
        Student student = new Student();
        student.setId(1001);
        student.setName("学员1(starter)" + this.properties.getName());
        return student;
    }

    @Bean
    public Student student2() {
        System.out.println("build student 2");
        Student student = new Student();
        student.setId(1002);
        student.setName("学员2(starter)");
        return student;
    }

}
