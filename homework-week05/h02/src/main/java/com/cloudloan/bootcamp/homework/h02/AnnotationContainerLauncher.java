package com.cloudloan.bootcamp.homework.h02;

import com.cloudloan.bootcamp.homework.h02.base.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 注解方式装配 Java Bean
 *
 * @author zhaochen
 */
@Configuration
public class AnnotationContainerLauncher {

    /**
     * @param args args
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "com.cloudloan.bootcamp.homework.h02.base",
                "com.cloudloan.bootcamp.homework.h02.config");
        final School school = (School) context.getBean("school");
        school.ding();
    }

}
