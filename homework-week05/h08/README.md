作业 h08 功能说明
1. application 为简单的 spring boot 项目，仅为引入 starter；
2. starter 为 spring-boot-starter 项目，包含作为 starter 的基本配置，以及自动装配的条件描述、配置；
3. application 启动后，读取 application.yml 配置文件，当配置包含 bootcamp-homework-starter.enabled=true，则满足 starter 自动装配条件；
4. 满足装配条件后 starter 项目的 School、Klass、Student 开始执行自动装配；