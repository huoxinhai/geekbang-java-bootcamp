package com.cloudloan.bootcamp.homework.h01.gateway.inbound;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

/**
 * 初始化 netty inbound 请求 {@link ChannelPipeline}，并为其配置自定义的 {@link ChannelInboundHandler}
 *
 * @author zhaochen
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 代理的后端服务列表
     */
    private final List<String> proxyServer;

    public HttpInboundInitializer(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        // 自定义 handler
        p.addLast(new HttpInboundHandler(this.proxyServer));
    }
}
