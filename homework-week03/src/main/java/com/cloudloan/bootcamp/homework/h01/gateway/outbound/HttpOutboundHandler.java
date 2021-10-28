package com.cloudloan.bootcamp.homework.h01.gateway.outbound;

import com.cloudloan.bootcamp.homework.h01.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * http 对外请求 handler
 *
 * @author zhaochen
 */
public interface HttpOutboundHandler {

    /**
     * 发出对外请求
     *
     * @param fullRequest       {@link FullHttpRequest}
     * @param ctx               {@link ChannelHandlerContext}
     * @param requestFilterList {@link HttpRequestFilter} 集合
     */
    void handle(FullHttpRequest fullRequest,
                ChannelHandlerContext ctx,
                List<HttpRequestFilter> requestFilterList);

}
