package com.sxia.zookeeper.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

@Slf4j(topic = "ZkHelper")
public class ZkHelper {
    private static final String connectUri = "localhost:2181";
    private static final int sessionTimeout = 20000; //超时时间
    private static ZooKeeper zkClient = null;//zk的客户端，相当于zkCli.sh
    private static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
    public ZkHelper() {
        try {
            zkClient = connect();
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
    public ZkHelper(Watcher watcher){
        try {
            zkClient = new ZooKeeper(connectUri,sessionTimeout,watcher);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    /**
     * 建立一个{@link ZooKeeper}连接
     *
     * @return {@link ZooKeeper}连接
     * @throws IOException          IO异常
     * @throws InterruptedException 中断异常
     */
    private ZooKeeper connect() throws IOException, InterruptedException {
        zkClient = new ZooKeeper(connectUri, sessionTimeout, event -> {
            log.debug("ZooKeeper客户端初始化");
            //收到事件通知后的回调函数（用户的业务逻辑）
            log.debug("事件信息：事件类型{}--事件发生的结点的路径{}--服务器状态{}", event.getType(), event.getPath(), event.getState());
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) { //只有回调的状态值
                log.debug("客户端建立与服务器的连接");
                connectedSemaphore.countDown();//只有连接建立了才释放锁，让主线程继续运行
            }
        });
        connectedSemaphore.await(); //在主线程中堵塞，等待连接建立好
        log.debug("客户端主线程运行完");
        return zkClient;
    }
    
    /**
     * 关闭连接
     */
    public void close() throws InterruptedException {
        zkClient.close();
    }
    /**
     * 拿到连接uri
     */
    public String getConnectUri() {
        return connectUri;
    }
    /**
     * 拿到zookeeper连接
     *
     * @return zkClient
     */
    public ZooKeeper getZookeeper() {
        return zkClient;
    }

    /**
     * 获取指定结点的值
     *
     * @param path 结点路径
     * @return 返回byte[]类型的结点信息
     */
    public byte[] getData(String path) {
        byte[] data = null;
        try {
            Stat stat;
            if ((stat = zkClient.exists(path, false)) != null) {
                data = zkClient.getData(path, false, stat);
            } else {
                log.error("znode:{}不存在",path);
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException("取到znode：" + path + "出现问题！！", e);
        }
        return data;
    }

    /**
     * 获取指定结点的值
     *
     * @param path 结点路径
     * @return 返回结点信息
     */
    public String get(String path) {
        String data = null;
        try {
            Stat stat;
            if ((stat = zkClient.exists(path, false)) != null) {
                byte[] bt = zkClient.getData(path, false, stat);
                if(bt != null){
                    data = new String(bt, StandardCharsets.UTF_8);
                }else {
                    log.info("该结点{}中没有数据",path);
                }
            } else {
                log.error("znode:{}不存在",path);
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException("取到znode：" + path + "出现问题！！", e);
        }
        return data;
    }

    /**
     * 根据结点路径返回Stat对象
     */
    public Stat getStat(String path){
        Stat stat = null;
        try {
            stat = zkClient.exists(path, false);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        if (stat == null) {
            throw new RuntimeException("node路径[" + path + "]不存在");
        }
        return stat;
    }
    /**
     * 获取state格式化的信息
     */
    public String getStatInfo(String path){
        Stat stat;
        stat = getStat(path);
        assert stat != null;
        return printZnodeInfo(stat);
    }

    /**
     * 格式化{@link Stat} 信息
     *
     * @param stat {@link Stat}
     * @return 返回格式化信息
     */
    public static String printZnodeInfo(Stat stat) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("\n*******************************\n");
        sb.append("创建znode的事务id czxid:").append(stat.getCzxid()).append("\n");
        //格式化时间
        sb.append("创建znode的时间 ctime:").append(df.format(stat.getCtime())).append("\n");
        sb.append("更新znode的事务id mzxid:").append(stat.getMzxid()).append("\n");
        sb.append("更新znode的时间 mtime:").append(df.format(stat.getMtime())).append("\n");
        sb.append("更新或删除本节点或子节点的事务id pzxid:").append(stat.getPzxid()).append("\n");
        sb.append("子节点数据更新次数 cversion:").append(stat.getCversion()).append("\n");
        sb.append("本节点数据更新次数 dataVersion:").append(stat.getVersion()).append("\n");
        sb.append("节点ACL(授权信息)的更新次数 aclVersion:").append(stat.getAversion()).append("\n");
        if (stat.getEphemeralOwner() == 0) {
            sb.append("本节点为持久节点\n");
        } else {
            sb.append("本节点为临时节点,创建客户端id为:").append(stat.getEphemeralOwner()).append("\n");
        }
        sb.append("数据长度为:").append(stat.getDataLength()).append("字节\n");
        sb.append("子节点个数:").append(stat.getNumChildren()).append("\n");
        sb.append("\n*******************************\n");
        return sb.toString();
    }
    /**
     * 创建结点
     *
     * <p>先复习一下zk中的ACL情况：格式为 schema:id:permission<br/>
     * 对应含义为：<ul>
     * <li>schema可选:world    ip    digest    auth</li>
     * <li>id可选:    anyone   ip地址 用户名:密码 用户</li>
     * <li>permission权限列表: {@code cdrwa}</li></ul>
     *
     * <p>{@link ZooDefs.Ids#ANYONE_ID_UNSAFE}表示[world:anyone]<br/>
     * {@link ZooDefs.Ids#OPEN_ACL_UNSAFE}:ANYONE_ID_UNSAFE +
     * {@link org.apache.zookeeper.ZooDefs.Perms#ALL}[READ | WRITE | CREATE | DELETE | ADMIN]
     *
     * <p>授予权限详情为:<ul>
     * <li>READ = 1 << 0  任为1</li>
     * <li>WRITE = 1 << 1  为10</li>
     * <li>CREATE = 1 << 2  为100</li>
     * <li>DELETE = 1 << 3  为1000</li>
     * <li>ADMIN = 1 << 4  为10000</li>
     * <li>READ | WRITE | CREATE | DELETE | ADMIN -> 运算结果为: 11111 即31，表示所有权限</li></ul>
     *
     * @param path 结点路径
     * @param data 结点中存放的数据
     */
    public String create(String path, byte[] data, ArrayList<ACL> aclList, CreateMode createMode){
        //先要拿到连接
        String resultPath = null;
        try {
            resultPath = zkClient.create(path, data, aclList, createMode);
            log.debug("结点[{}]创建成功", resultPath);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return resultPath;
    }

    public void createAndSetDefaultAcl(String path, byte[] data) {
        create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

}