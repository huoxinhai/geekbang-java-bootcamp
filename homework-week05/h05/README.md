作业 05 （选做）总结一下，单例的各种写法，比较它们的优劣。

## 饿汉式

- 延迟初始化：否
- 线程安全：是
- 实现难度：容易

通过 `classloader` 机制避免多线程问题，但类加载时就实例化，可能会有一定的内存浪费；

```java
public class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }
}
```

## 懒汉式 - 线程不安全

- 延迟初始化：是
- 线程安全：否
- 实现难度：容易

由于没有锁，实际并非严格的单例模式；

```java
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

## 懒汉式 - 线程安全

- 延迟初始化：是
- 线程安全：是
- 实现难度：容易

通过增加 `synchronized` 关键字实现了线程安全，但是效率非常低，每次 getInstance() 都要先拿到锁；

```java
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

## 懒汉式 - 双重锁校验

- 延迟初始化：是
- 线程安全：是
- 实现难度：略复杂

采用双锁机制，安全且在多线程情况下 getInstance() 能保持高性能

```java
public class Singleton {

    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

## 静态内部类

- 延迟初始化：是
- 线程安全：是
- 实现难度：中等，但不好理解

Singleton 类加载时并不会初始化实例，仅当 `getInstance()` 时，触发加载 SingletonHolder 类进一步实例化 Singleton，实现懒加载 + 天生线程安全

```java
public class Singleton {
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

## 枚举式

- 延迟初始化：否
- 线程安全：是
- 实现难度：容易，但少见

《Effective Java》作者推荐，通过枚举形式还可以避免通过序列化、反序列化方式构建出多个实例

```java
public enum Singleton {
    INSTANCE;
}
```