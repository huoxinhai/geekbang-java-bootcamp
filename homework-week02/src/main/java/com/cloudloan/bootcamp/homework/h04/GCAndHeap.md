4.（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。



### 堆的默认值

机器内存 >=1GB， Xmx=1/4 机器内存；

机器内存 <1GB， Xmx=1/2 机器内存；



### 堆的划分

Young：年轻代，存放新创建的对象

Old：老年代，存放长期存活的对象

基于分代假设原理，针对不同的堆，用不同的策略优化内存占用



### 堆的蓄水池原理

堆蓄水池原理，内存越大，GC频率降低，但 STW 时间变长；



### GC 分类

Young GC：清理 Eden + S0/S1 区

Full GC：清理 Young 区 + Old 区



### GC 自适应算法

JVM 内部包含自适应算法，会根据运行情况进行分析，从而自动调整 heap 各个区域的内存大小；



### 串行 GC（-XX:+UseSerialGC）

#### Serial

运行方式：串行

清理区域：Young

清理算法：复制

运行目标：优先应用响应速度

适用场景：单 CPU场景下 client 模式

问题：未能充分发挥现在多核心CPU的优势；STW 时间会较长；



#### Serial Old

运行方式：串行

清理区域：Old

清理算法：标记-整理

运行目标：优先应用响应速度

适用场景：单 CPU场景下 client 模式



### 并行 GC（-XX:+UseParallelGC）

#### ParNew

运行方式：并行

清理区域：Young

清理算法：复制

运行目标：优先应用响应速度

适用场景：Java 8 默认GC算法，server 模式



#### ParallelOld

运行方式：并行

清理区域：Old

清理算法：标记-清理

运行目标：优先应用吞吐量

适用场景：Java 8 默认GC算法，server 模式



### CMS GC（-XX:+UseConcMarkSweepGC）

运行方式：并发

清理区域：Old

算法：标记 + 清除

运行目标：优先应用响应速度

适用场景：B/S系统，server 模式



### G1 GC（-XX:+UseG1GC）

运行方式：并发

清理区域：Young + Old

算法：标记 + 整理 + 复制

运行目标：优先应用响应速度

适用场景：B/S系统，server 模式，未来会替换 CMS

问题：

1. 逻辑最为复杂；
2. G1若发生退化，则直接变为 Serial Old 模式；退化情况：
   1. 并发模式失败
   2. 晋升失败
   3. 巨型对象分配失败



### GC 常见组合方式

- Serial + Serial Old，单线程低延迟；

- ParNew + CMS，多线程低延迟；

- Parallel Scavenge + Parallel Old 多线程高吞吐量；

- G1



### 堆及GC配置注意

配置堆及GC时，尽量不要依赖 JVM 的默认值，避免出现环境问题导致 JVM 默认值初始化失败；

例如 docker 环境下未能做到资源配置隔离，导致CPU核心数、内存数读取错误，引发GC策略执行异常；





