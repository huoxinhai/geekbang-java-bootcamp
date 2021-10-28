package com.cloudloan.bootcamp.homework.h02;

import java.util.concurrent.*;

/**
 * @author yeke
 * @date 2021/10/17 6:33 下午
 */
public class Way01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        ExecutorService executor = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        Future<Integer> result = executor.submit(Way01::sum);
        executor.shutdown();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result.get());
//        Thread.sleep(1000);
        System.out.println("主线程关闭使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
