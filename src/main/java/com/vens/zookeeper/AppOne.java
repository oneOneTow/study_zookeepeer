package com.vens.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/9/20
 */
public class AppOne {
    private ZkClient zkClient;
    public void init(){
        String connectStr="";
        int connectTimeout=30000;
        this.zkClient=new ZkClient(connectStr,connectTimeout);
        String zNode="/GroupMemebers/"+this.getClass().getName();
        if(!zkClient.exists(zNode)){
            zkClient.create(zNode,new String("app one"), CreateMode.EPHEMERAL);
        }
    }
    public static void main( String[] args ) {


        }
}
