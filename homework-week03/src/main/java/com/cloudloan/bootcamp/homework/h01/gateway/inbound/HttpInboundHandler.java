package com.cloudloan.bootcamp.homework.h01.gateway.inbound;

import com.cloudloan.bootcamp.homework.h01.gateway.filter.HeaderHttpRequestFilter;
import com.cloudloan.bootcamp.homework.h01.gateway.filter.HttpRequestFilter;
import com.cloudloan.bootcamp.homework.h01.gateway.outbound.HttpOutboundHandler;
import com.cloudloan.bootcamp.homework.h01.gateway.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * netty inbound 请求处理器
 *
 * @author zhaochen
 */
@Slf4j
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    /**
     * 代理的后端服务列表
     */
    private final List<String> proxyServer;

    /**
     * 自定义请求外部的处理器
     */
    private final HttpOutboundHandler handler;

    /**
     * 自定义请求过滤器
     */
    private final List<HttpRequestFilter> filterList = new ArrayList<>();

    public HttpInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
        this.handler = new OkhttpOutboundHandler(this.proxyServer);
        this.filterList.add(new HeaderHttpRequestFilter());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final long startMillis = System.currentTimeMillis();
        try {
            log.info("网关处理请求 开始");
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            log.info("网关处理请求 uri:{}", uri);
            this.handler.handle(fullRequest, ctx, filterList);
            log.info("网关处理请求 完成, uri:{}, 用时:{}ms", uri, (System.currentTimeMillis() - startMillis));
        } catch (Exception e) {
            log.error("网关处理请求 异常:{}", e.getMessage(), e);
        } finally {
            // todo 待学习，调用该方法会出现 io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
            ReferenceCountUtil.release(msg);
        }
    }

}
