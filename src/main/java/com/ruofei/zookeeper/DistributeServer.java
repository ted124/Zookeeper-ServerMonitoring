package com.ruofei.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        DistributeServer server = new DistributeServer();

        // 1. 连接zookeeper集群
        server.getConnect();

        // 2. 注册节点
        server.regist(args[0]);

        // 3. 业务逻辑处理
        server.business();
    }

    private void business() throws InterruptedException {

        Thread.sleep(Long.MAX_VALUE);
    }

    private void regist(String hostName) throws KeeperException, InterruptedException {

        String path = zkClient.create("/servers/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(hostName + "is online");
    }

    private String connectString = new String("hadoop102:2181,hadoop103:2181,hadoop104:2181");
    private int sessionTimeout = 2000;
    ZooKeeper zkClient;

    private void getConnect() throws IOException {

        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
