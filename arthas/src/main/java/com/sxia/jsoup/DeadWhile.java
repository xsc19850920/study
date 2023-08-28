package com.sxia.jsoup;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DeadWhile {
    static ReentrantLock lock = new ReentrantLock();
     static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        hashMap.put(1, "");
//        simple();
//        concurrent();
//        System.out.println(DateUtil.format(new Date(), df.get()));

//        System.out.println(df.get().format(new Date()));
    }

    private static void concurrent() {
        AtomicInteger ticketConcurrent = new AtomicInteger(100);

        Thread thread1 = new Thread(()->{
            while(ticketConcurrent.get()> 0){
                log.info(Thread.currentThread().getName() + "当前票号:{}" + ticketConcurrent.get() + "剩余票量:{}" + ticketConcurrent.getAndAdd(-1));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(()->{
            while(ticketConcurrent.get()> 0){
                log.info(Thread.currentThread().getName() + "当前票号:{}" + ticketConcurrent.get() + "剩余票量:{}" + ticketConcurrent.getAndAdd(-1));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread2.start();
    }
    /**
     * 枷锁的方式
     */
    private static void simple() {
        Thread thread1 = new Thread(new TicketRunnable());
        thread1.start();
        Thread thread2 = new Thread(new TicketRunnable());
        thread2.start();
    }


    //实现Runnable接口并重写run方法
    static class TicketRunnable implements Runnable {
       static Integer ticket = 100;

        @Override
        public void run() {
            //卖票
            lock.lock();
            while (ticket > 0) {
                log.info(Thread.currentThread().getName() + "当前票号:{}" + ticket + "剩余票量:{}" + --ticket);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            lock.unlock();
        }
    }

}
