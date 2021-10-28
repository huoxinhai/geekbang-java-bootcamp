package com.cloudloan.bootcamp.homework.starter.base;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kimmking
 */
@Component
@Data
public class Klass {

    @Resource
    List<Student> students;

    public void dong() {
        System.out.println(this.getStudents());
    }

}
