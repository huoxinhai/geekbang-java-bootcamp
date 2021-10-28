package com.cloudloan.bootcamp.homework.h01.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * http 响应过滤器
 *
 * @author zhaochen
 */
public interface HttpRequestFilter {

    /**
     * 过滤请求
     *
     * @param fullRequest {@link FullHttpRequest}
     * @param ctx         {@link ChannelHandlerContext}
     */
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
