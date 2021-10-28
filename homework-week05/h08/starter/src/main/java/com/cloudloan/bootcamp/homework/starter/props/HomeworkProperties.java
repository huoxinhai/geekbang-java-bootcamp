package com.cloudloan.bootcamp.homework.starter.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homework starter 使用配置
 *
 * @author Zhaochen
 */
@ConfigurationProperties(prefix = "bootcamp-homework-starter")
@Getter
@Setter
@ToString
public class HomeworkProperties {

    /**
     * default config
     */
    private String name = "defaultName";

}
