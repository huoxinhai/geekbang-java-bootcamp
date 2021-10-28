package com.cloudloan.bootcamp.homework.h01;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * homework-01：（选做）使 Java 里的动态代理，实现一个简单的 AOP。
 *
 * @author zhaochen
 */
public class JavaDynamicProxy {

    /**
     * launch
     *
     * @param args args
     */
    public static void main(String[] args) {
        // JDK 代理目标
        final ISchool school = new School();
        final ISchool schoolProxy = buildDynamicProxy(school);
        // 得到 com.cloudloan.bootcamp.homework.h01.$Proxy0
        System.out.println("构建代理:" + schoolProxy.getClass().getName());
        schoolProxy.ding("极客时间");

        System.out.println("--------------------");

        // Cglib 代理目标
        final Student student = new Student();
        final Student studentProxy = buildDynamicProxy(student);
        // 得到: com.cloudloan.bootcamp.homework.h01.Student$$EnhancerByCGLIB$$9b93dbca
        System.out.println("构建代理:" + studentProxy.getClass().getName());
        studentProxy.dong("学员");
    }

    /**
     * 构建基于接口 + 接口实现类，构建动态代理实现类
     * <p>
     * 功能：在方法调用前后打印调用日志
     *
     * @param targetInstance 代理目标
     * @param <T>            接口范型
     * @return 动态代理增强类
     */
    private static <T> T buildDynamicProxy(T targetInstance) {
        // 判断代理目标类是否实现了接口
        // 若实现接口，使用 JDK Proxy，否则使用 Cglib
        final Class<?>[] targetInterfaces = targetInstance.getClass().getInterfaces();
        if (targetInterfaces.length > 0) {
            // 使用 JDK 动态代理
            return (T) Proxy.newProxyInstance(
                    targetInstance.getClass().getClassLoader(),
                    targetInterfaces,
                    new InvokeAopByJdkProxy(targetInstance));
        } else {
            // 使用 Cglib 动态代理
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(targetInstance.getClass());
            enhancer.setCallback(new InvokeAopByCglibProxy<T>(targetInstance));
            return (T) enhancer.create();
        }
    }

}

/**
 * 方法调用前后输出调用日志 - JDK 动态代理实现
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
class InvokeAopByJdkProxy<T> implements InvocationHandler {

    private final T target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("[JDK Proxy] 请求开始, 调用:" + proxy.getClass().getName() + "#" + method.getName() + ", 参数:" + Arrays.toString(args));
        final Object result = method.invoke(target, args);
        System.out.println("[JDK Proxy] 请求完成, 返回:" + result);
        return result;
    }
}

/**
 * 方法调用前后输出调用日志 - Cglib 动态代理实现
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
class InvokeAopByCglibProxy<T> implements MethodInterceptor {

    private final T target;

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("[Cglib Proxy] 请求开始, 调用:" + this.target.getClass().getName() + "#" + method.getName() + ", 参数:" + Arrays.toString(args));
        Object result = method.invoke(target, args);
        System.out.println("[Cglib Proxy] 请求完成, 返回:" + result);
        return result;
    }
}

/**
 * 接口
 */
interface ISchool {

    /**
     * ding
     *
     * @param name name
     * @return message
     */
    String ding(String name);

}

/**
 * 接口实现类
 */
class School implements ISchool {

    @Override
    public String ding(String name) {
        String result = "welcome " + name;
        System.out.println("方法执行, ding!");
        return result;
    }

}

/**
 * 无接口实现类
 */
class Student {

    public String dong(String name) {
        String result = "hi " + name;
        System.out.println("方法执行, dong!");
        return result;
    }

}

