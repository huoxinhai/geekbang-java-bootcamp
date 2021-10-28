package com.cloudloan.bootcamp.homework.h01.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * {@link HttpRequestFilter} 实现
 *
 * @author zhaochen
 */
public class HeaderHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest request, ChannelHandlerContext ctx) {
        // http header 中添加自定义信息
        request.headers().add("geekbang-java-bootcamp-req", "zhaochen add request header");
    }

}
