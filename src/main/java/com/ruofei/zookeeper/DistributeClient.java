package com.ruofei.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        DistributeClient client = new DistributeClient();

        // 1. 获取zookeeper集群连接
        client.getConnect();

        // 2. 注册监听
        client.getChildren();

        // 3. 业务逻辑处理
        client.business();
    }

    private void business() throws InterruptedException {

        Thread.sleep(Long.MAX_VALUE);
    }

    private void getChildren() throws KeeperException, InterruptedException {

        List<String> children = zkClient.getChildren("/servers", true);

        // 储存服务器节点主机名称
        ArrayList<String> hosts = new ArrayList<String>();

        for (String child : children) {

            byte[] data = zkClient.getData("/servers/" + child, false, null);

            hosts.add(new String(data));
        }

        // 打印所有节点到控制台
        System.out.println(hosts);
    }

    private String connectString = new String("hadoop102:2181,hadoop103:2181,hadoop104:2181");
    private int sessionTimeout = 2000;
    ZooKeeper zkClient;

    private void getConnect() throws IOException {

        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

                try {
                    getChildren();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
