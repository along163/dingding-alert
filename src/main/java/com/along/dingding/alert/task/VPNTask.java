package com.along.dingding.alert.task;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import cn.snowheart.dingtalk.robot.starter.entity.DingTalkResponse;
import com.along.dingding.alert.util.NetWorkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
@Slf4j
public class VPNTask {
    //正式
    private static String keyWord = "日志收集";
    //测试
//    private static String keyWord = "vpn网络";

    @Autowired
    @Qualifier("dingTalkRobotClient")
    private DingTalkRobotClient client;

//    private int i;

    //每隔15秒执行一次方法
//    @Scheduled(cron = "*/50 * * * * ?")
//    public void execute() {
//        log.info("----定时任务thread id:{},FixedPrintTask execute times:{}", Thread.currentThread().getId(), ++i);
//    }

    //20分钟执行一次
//    @Async
    @Scheduled(cron = "0 0/20 * * * ?")
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void vpnTest() {

        log.info("vpn网络检测开始");

        ArrayList<String> ipList = new ArrayList<>();
        //kafka001
        ipList.add("172.20.4.1");
        ipList.add("172.20.4.2");
        ipList.add("172.20.4.0");
        //测试
//        ipList.add("192.168.204.11");
//        ipList.add("192.168.204.12");
//        ipList.add("192.168.204.13");

        int cont = 0;

        for (String ip : ipList) {
            if (!NetWorkUtils.isAddressAvailable(ip)) {
                cont++;
                log.error("----------vpn网络连接失败数量：{}", cont);
            } else {
                log.info("------------{}网络畅通", ip);
            }
        }
        try {
            if (cont > 1) {
                log.error("vpn网络断开连接，发送钉钉告警！");
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DingTalkResponse dingTalkResponse = client.sendTextMessage(sdf.format(d) + ":" + keyWord + "网络连接断开了，" + ipList.toString() + "，请及时修复!");
                log.info("钉钉告警发送响应：{}", dingTalkResponse.toString());
            }

        } catch (Exception e) {
            log.error("vpn网络检测异常，异常信息为：{}", e.getMessage());
            e.printStackTrace();
        }
    }


}
