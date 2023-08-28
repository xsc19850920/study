package com.sxia.juc.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * PriorityBlockingQueue（优先阻塞队列）是Java并发包java.util.concurrent下面的一个工具类，它除了具有阻塞队列的功能外还具有以下特点：
 *
 * 对队列中的元素进行排序，如果未指定比较器，插入队列的元素必须实现Comparable接口
 * 内部基于数组实现的最小二叉堆算法
 * 队列的长度是可扩展的（类似ArrayList），上限为Integer.MAX_VALUE - 8
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<Person> queue = new PriorityBlockingQueue<Person>(15,(a,b)-> a.getAge() >= b.getAge()  ? -1:1);
        for (int i = 0; i < 10; i++) {
            queue.offer(new Person(""+i,i));
        }
        queue.offer(new Person(""+80,80));
        queue.offer(new Person(""+90,90));

        Person penson = null;
        do {
            penson = queue.poll();
            if (penson != null) {
                System.out.println(penson.getName() + "挂号成功！");
            }
        } while (penson != null);

    }

}

@Data
@AllArgsConstructor
class Person {
    private String name;
    private int age;
}