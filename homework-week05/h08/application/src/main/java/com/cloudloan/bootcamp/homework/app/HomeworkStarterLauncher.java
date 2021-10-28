package com.cloudloan.bootcamp.homework.app;

import com.cloudloan.bootcamp.homework.starter.base.ISchool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Zhaochen
 */
@SpringBootApplication
public class HomeworkStarterLauncher {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(HomeworkStarterLauncher.class, args);
        // 若 starter 未满足自动装配条件，下方代码抛出 NoSuchBeanDefinitionException
        ISchool school = context.getBean(ISchool.class);
        school.ding();
    }

}
