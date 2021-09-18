package com.along.dingding.alert.util;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 判断网络连接状况.
 * @author nagsh
 *
 */
@Slf4j
public class NetWorkUtils {

    public static boolean isConnect(String ip){
        log.info("--------------检测{}开始",ip);
        boolean connect = false;
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec("ping " + ip);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
//            System.out.println("返回值为:"+sb);
            is.close();
            isr.close();
            br.close();

            if (null != sb && !sb.toString().equals("")) {
                String logString = "";
                if (sb.toString().indexOf("TTL") > 0) {
                    // 网络畅通
                    log.info("--------------{}网络畅通",ip);
                    connect = true;
                } else {
                    // 网络不畅通
                    log.info("--------------{}网络不通",ip);
                    connect = false;
                }
            }
        } catch (IOException e) {
            log.error("--------------{}网络检测异常",ip);
            e.printStackTrace();
        }
        return connect;
    }


    public static boolean isAddressAvailable(String ip){
        try{
            InetAddress address = InetAddress.getByName(ip);//ping this IP

            if(address instanceof java.net.Inet4Address){
                log.info(ip + " is ipv4 address");
            }else
            if(address instanceof java.net.Inet6Address){
                log.info(ip + " is ipv6 address");
            }else{
                log.info(ip + " is unrecongized");
            }

            if(address.isReachable(5000)){
                log.info("SUCCESS - ping " + ip );
                return true;
            }else{
                log.info("FAILURE - ping " + ip );
                return false;
            }

//            System.out.println("\n-------Trying different interfaces--------\n");
//
//            Enumeration<NetworkInterface> netInterfaces =
//                    NetworkInterface.getNetworkInterfaces();
//            while(netInterfaces.hasMoreElements()) {
//                NetworkInterface ni = netInterfaces.nextElement();
//                System.out.println(
//                        "Checking interface, DisplayName:" + ni.getDisplayName() + ", Name:" + ni.getName());
//                if(address.isReachable(ni, 0, 5000)){
//                    System.out.println("SUCCESS - ping " + ip);
//                }else{
//                    System.out.println("FAILURE - ping " + ip);
//                }
//
//                Enumeration<InetAddress> ips = ni.getInetAddresses();
//                while(ips.hasMoreElements()) {
//                    System.out.println("IP: " + ips.nextElement().getHostAddress());
//                }
//                System.out.println("-------------------------------------------");
//            }
        }catch(Exception e){
            log.error("--------------{}网络检测异常",ip);
            e.printStackTrace();
        }
        return false;
    }



            public static void main(String[] args) {
        NetWorkUtils netState = new NetWorkUtils();
//        System.out.println(netState.isConnect("172.20.4.1"));

                System.out.println(netState.isAddressAvailable("172.20.4.10"));

            }

}
