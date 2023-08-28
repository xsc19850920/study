package com.sxia.juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockQueueTest {

    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<Integer>(3);
        /**
         * add 增加一个元索 如果队列已满，则抛出一个IIIegaISlabEepeplian异常
         * remove 移除并返回队列头部的元素 如果队列为空，则抛出一个NoSuchElementException异常
         * element 返回队列头部的元素 如果队列为空，则抛出一个NoSuchElementException异常
         * offer 添加一个元素并返回true 如果队列已满，则返回false
         * poll 移除并返问队列头部的元素 如果队列为空，则返回null
         * peek 返回队列头部的元素 如果队列为空，则返回null
         * put 添加一个元素 如果队列满，则阻塞
         * take 移除并返回队列头部的元素 如果队列为空，则阻塞
         */
//        Stream.of(1,2,3).forEach(blockingQueue::add);
//        blockingQueue.remove();
//        blockingQueue.remove();
//        blockingQueue.remove();
//        blockingQueue.remove();
//        //element 指向对应的第一个元素信息
//        Integer element = blockingQueue.element();
//        System.out.println(element);


//        Stream.of(1,2,3).forEach(blockingQueue::offer);
//        Integer element = blockingQueue.poll();
//        blockingQueue.peek();
//        System.out.println(element);


    }
}
