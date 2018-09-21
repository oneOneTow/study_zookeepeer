package com.vens.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/9/20
 */
public class ZkClientWatcher {
    private static String url="";
    private static int timeout=30000;
    public static void main(String[]args){
        ZkClient zkc = new ZkClient(url, timeout);
        zkc.deleteRecursive("/test");
        //对父节点添加监听子节点变化。
        zkc.subscribeChildChanges("/test", new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("parentPath: " + parentPath);
                System.out.println("currentChilds: " + currentChilds);
            }

        });
        //监听节点数据变化：包括节点数据变更和节点删除
        zkc.subscribeDataChanges("/test", new IZkDataListener() {

            public void handleDataDeleted(String path) throws Exception {
                System.out.println("删除的节点为:" + path);
            }


            public void handleDataChange(String path, Object data) {
                System.out.println("变更的节点为:" + path + ", 变更内容为:" + data);
            }
        });

        zkc.subscribeStateChanges(new IZkStateListener() {
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                if(state==Watcher.Event.KeeperState.SyncConnected){
                    //当我重新启动后start，监听触发
                    System.out.println("连接成功");
                }else if(state==Watcher.Event.KeeperState.Disconnected){
                    System.out.println("连接断开");//当我在服务端将zk服务stop时，监听触发
                }else{
                    System.out.println("其他状态"+state);
                }
            }
            public void handleNewSession() throws Exception {
                System.out.println("---->重建session");
            }
        });


    }
}
