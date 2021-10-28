package com.cloudloan.bootcamp.homework.h01.gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author zhaochen
 */
@AllArgsConstructor
@Slf4j
public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {

    private final Consumer<Object> consumer;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端 channelActive！！！");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端 channelRead！！！msg 类型:{}", msg.getClass().getName());
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            final String str = buf.toString(CharsetUtil.UTF_8);
            System.out.println("CONTENT:" + str);
            buf.release();
            this.consumer.accept(str);
        }
    }

}