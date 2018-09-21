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
        //�Ը��ڵ���Ӽ����ӽڵ�仯��
        zkc.subscribeChildChanges("/test", new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("parentPath: " + parentPath);
                System.out.println("currentChilds: " + currentChilds);
            }

        });
        //�����ڵ����ݱ仯�������ڵ����ݱ���ͽڵ�ɾ��
        zkc.subscribeDataChanges("/test", new IZkDataListener() {

            public void handleDataDeleted(String path) throws Exception {
                System.out.println("ɾ���Ľڵ�Ϊ:" + path);
            }


            public void handleDataChange(String path, Object data) {
                System.out.println("����Ľڵ�Ϊ:" + path + ", �������Ϊ:" + data);
            }
        });

        zkc.subscribeStateChanges(new IZkStateListener() {
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                if(state==Watcher.Event.KeeperState.SyncConnected){
                    //��������������start����������
                    System.out.println("���ӳɹ�");
                }else if(state==Watcher.Event.KeeperState.Disconnected){
                    System.out.println("���ӶϿ�");//�����ڷ���˽�zk����stopʱ����������
                }else{
                    System.out.println("����״̬"+state);
                }
            }
            public void handleNewSession() throws Exception {
                System.out.println("---->�ؽ�session");
            }
        });


    }
}
