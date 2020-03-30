package com.gsg;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JVMDemo
 * @Description TODO
 * @Author mc
 * @Date 2019/12/19 10:15
 * @Version 1.0
 **/
public class JVMDemo {

    byte[] a = new byte[100*1024*1024];

    public static void main(String[] args) {
        List<Object> list= new ArrayList<>();
        int i =1;
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println("初始化堆的当前内存使用量:"+memoryMXBean.getHeapMemoryUsage());
        System.out.println("初始化非堆内存的当前内存使用量:"+memoryMXBean.getNonHeapMemoryUsage());
        while (true){
            list.add(new JVMDemo());
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("非堆内存的当前内存使用量:"+memoryMXBean.getNonHeapMemoryUsage());
            System.out.println("堆内存的当前内存使用量:"+memoryMXBean.getHeapMemoryUsage());
            System.out.println(i++);
        }

    }

}
