package com.sxia.zookeeper.client;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Test1_ZkHelper")
public class Test1_ZkHelper {
    public static void main(String[] args) {
        ZkHelper zkHelper = new ZkHelper();
        zkHelper.createAndSetDefaultAcl("/MyFirstZkNode","This is my first Zookeeper Node!".getBytes());
    }
}
