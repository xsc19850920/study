package com.sxia.juc.queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        //SynchronousQueue 构造参数是false的话表示非公平锁  true/公平锁 默认是false
        //公平锁情况下
        //1
        //size=> 0
        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(true);
        //非公平锁的情况下结果
        // 2
        //size=> 0
//        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(false);
        new Thread(() -> {
            try {
                queue.put(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        //休眠一下，让异步线程完成
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                queue.put(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        //休眠一下，让异步线程完成
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        //休眠一下，让异步线程完成
        TimeUnit.SECONDS.sleep(1);
        //不管如何输出，都是 0
        System.out.println("size=> "+queue.size());
    }

}
