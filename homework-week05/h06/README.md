作业 06 （选做）maven/spring 的 profile 机制，都有什么用法？

spring 通过 profiles 机制，实现对同一程序在多个环境下，使用不同的配置内容，实现程序与环境的分离

Spring profiles 有两个变量可以配置

- spring.profiles.default 默认值，优先级低。当 active 没有配置时使用此变量；
- spring.profiles.active 优先级高，指定当前 spring 程序使用哪个 profile；

使用方式包括：

1. 区分装配不同环境的 bean
    1. 在类上 `@Profile("dev")`
    2. XML 中 `<beans profile="dev">`

2. 激活 profiles
    1. 启动类 `@ActiveProfiles`
    2. 程序启动后 `AnnotationConfigApplicationContext#getEnvironment().setActiveProfiles("dev")`;
    3. 启动命令行参数指定，`-Dspring.profiles.active=dev(test/qa/prod)`;
    4. Linux 操作系统环境变量 `export spring_profiles_active=dev`
    5. 打包在应用程序内的 application.properties 或者 appliaction.yml 文件中配置;

上述配置使用优先级，从1～7

