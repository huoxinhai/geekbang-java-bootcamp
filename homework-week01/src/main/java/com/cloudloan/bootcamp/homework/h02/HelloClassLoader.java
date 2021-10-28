package com.cloudloan.bootcamp.homework.h02;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 自定义 classloader
 *
 * @author zhaochen
 */
public class HelloClassLoader extends ClassLoader {

    /**
     * @param args args
     * @throws Exception Exception
     */
    public static void main(String[] args) throws Exception {
        final String clazzFile = "Hello.xlass";
        final String clazzName = "Hello";
        final String methodName = "hello";
        // 加载类文件字节数组并进行解密
        byte[] clazzBytes = loadClazzBytes(clazzFile);
        // 创建自定义类加载器加载 Hello 类
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class<?> clazz = helloClassLoader.defineClass(clazzName, clazzBytes, 0, clazzBytes.length);
        System.out.println("加载类:" + clazz.getName());
        // 反射执行 Hello.hello() 方法
        final Object instance = clazz.newInstance();
        final Method targetMethod = clazz.getDeclaredMethod(methodName);
        System.out.println("----------------");
        targetMethod.invoke(instance);
        System.out.println("----------------");
    }

    /**
     * 使用 AppClassLoader 加载当前应用 classpath 下的类文件
     *
     * @param classFile 类文件名称
     * @return 类文件字节数组
     * @throws IOException IOException
     */
    private static byte[] loadClazzBytes(String classFile) throws IOException {
        byte[] classBytes = new byte[0];
        ClassLoader appClassLoader = HelloClassLoader.class.getClassLoader();
        try (InputStream inputStream = appClassLoader.getResourceAsStream(classFile)) {
            if (Objects.nonNull(inputStream)) {
                classBytes = new byte[inputStream.available()];
                inputStream.read(classBytes);
                decryptClazzBytes(classBytes);
            }
        }
        return classBytes;
    }

    /**
     * 解密类文件字节数组
     * <p>
     * 算法：x=255-x
     *
     * @param classFileBytes 密文
     */
    private static void decryptClazzBytes(byte[] classFileBytes) {
        for (int i = 0; i < classFileBytes.length; i++) {
            classFileBytes[i] = (byte) (255 - classFileBytes[i]);
        }
    }

}
