package com.along.dingding.alert.app;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import cn.snowheart.dingtalk.robot.starter.entity.DingTalkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MySendMessage {

    private static String keyWord = "vpn网络";

    @Autowired
    @Qualifier("dingTalkRobotClient")
    private  DingTalkRobotClient client;

    public void sendMsg(){
        DingTalkResponse dingTalkResponse = client.sendTextMessage(keyWord+"断了，请及时修复");
//        Assert.assertEquals(dingTalkResponse.getErrcode().longValue(), 0L);
        System.out.println(dingTalkResponse.toString());
    }



    public static void main(String[] args) {

        new MySendMessage().sendMsg();

    }
}
