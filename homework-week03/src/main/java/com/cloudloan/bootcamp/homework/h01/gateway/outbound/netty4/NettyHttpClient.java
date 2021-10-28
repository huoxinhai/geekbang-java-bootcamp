package com.cloudloan.bootcamp.homework.h01.gateway.outbound.netty4;

import com.cloudloan.bootcamp.homework.h01.gateway.outbound.AbstractHttpOutboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 后端服务请求发起实现方式 - netty http client
 *
 * @author zhaochen
 */
@Slf4j
public class NettyHttpClient extends AbstractHttpOutboundHandler {

    public NettyHttpClient(List<String> backendUrls) {
        super(backendUrls);
    }

    public void connect(String host, int port, FullHttpRequest request, Consumer<Object> consumer) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new NettyHttpClientOutboundHandler(consumer));
                }
            });
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
            log.info("netty client 建立连接");

            // 发送请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void doHandle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String targetUrl) {
        // 提取 targetUrl 中的 ip + port
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(targetUrl).build();
        final String host = uriComponents.getHost();
        final int port = uriComponents.getPort();
        final FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK);
        try {
            this.connect(host, port, fullRequest, (object) -> {
                fullHttpResponse.content().writeBytes(object.toString().getBytes(StandardCharsets.UTF_8));
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(fullHttpResponse);
                }
            }
            ctx.flush();
        }
    }

}