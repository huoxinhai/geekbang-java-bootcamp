package com.cloudloan.bootcamp.homework.h01.gateway;


import com.cloudloan.bootcamp.homework.h01.gateway.inbound.HttpInboundServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Launcher
 *
 * @author zhaochen
 */
@Slf4j
public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "3.0.0";

    public static void main(String[] args) {
        // 设置启动端口
        String proxyPort = System.getProperty("proxyPort", "8888");
        // 设置代理的后端服务列表
        String proxyServers = System.getProperty("proxyServers", "http://localhost:8801");
        int port = Integer.parseInt(proxyPort);
        log.info(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        HttpInboundServer server = new HttpInboundServer(port, Arrays.asList(proxyServers.split(",")));
        log.info(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        } catch (Exception ex) {
            log.error("{} 启动失败, {}", GATEWAY_NAME, ex.getMessage(), ex);
        }
    }
}
