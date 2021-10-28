作业 07 （选做）总结 Hibernate 与 MyBatis 的各方面异同点。

1. 开发速度对比

- Hibernate：需要开发人员先构建出良好的对象关系，如1:1, 1:n, n:n，形成 xml 配置文件
- MyBatis：根据查询需求，灵活组合表及映射返回结果

2. 开发工作量对比

- Hibernate：简单查询场景，几乎不用写 HQL，开发者只需要关心业务流程
- MyBatis：开发需要需要编写 SQL、mapper、ResultMap 等（但借助 MyBatis-Plus等框架可以减少大部分简单场景开发）

3. 对象管理对比

- Hibernate：完整的对象管理方案，包含对象状态概念，开发过程中需要关注游离态、持久态等状态，并注意使用情况；
- MyBatis：开发者自行管理

4. 缓存机制对比（还未能深入了解）

- Hibernate：一级缓存（session级）、二级缓存（SessionFactory级）
- MyBatis：提供很好的查询缓存特性，支持 LRU、FIFO、SOFT、WEAK 回收策略，支持 INSERT、UPDATE、DELETE 操作后缓存失效

5. DBA 对比
- Hibernate：通过 HQL + 字典 生成的执行 SQL 略丑，开发与DBA沟通时会有内容不对齐问题，优化时较为痛苦；一般查询字段都会被查出来，如果控制字段反而破坏开发便捷性；
- MyBatis：原生 SQL，查询可控，开发与DBA内容对齐；

大公司使用 MyBatis（自我理解）
1. MyBatis 入门简单，即学即用，不需要较高的对象关系模型设计意识和ORM设计权衡;
2. MyBatis 开发灵活，能够快速应对业务需求的变化，保证业务快速迭代上线，虽然不如 Hibernate 功能强大，但足以应对大部分场景；
3. SQL 执行效率可跟踪，开发配合DBA进行SQL优化时直接、快速；
