package com.cloudloan.bootcamp.homework.h02;

import java.util.concurrent.*;

/**
 * @author yeke
 * @date 2021/10/17 6:33 下午
 */
public class Way02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Integer> task = new FutureTask<>(Way02::sum);
        executor.submit(task);
        executor.shutdown();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+task.get());
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
