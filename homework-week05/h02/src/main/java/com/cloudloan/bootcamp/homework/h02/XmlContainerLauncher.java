package com.cloudloan.bootcamp.homework.h02;

import com.cloudloan.bootcamp.homework.h02.base.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * XML 配置文件方式装配 Java Bean
 *
 * @author zhaochen
 */
public class XmlContainerLauncher {

    /**
     * @param args args
     */
    public static void main(String[] args) {
        final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        final School school = (School) context.getBean("school");
        school.ding();
    }

}
