package com.cloudloan.bootcamp.homework.h01.gateway.outbound.httpclient4;

import com.cloudloan.bootcamp.homework.h01.gateway.filter.HeaderHttpResponseFilter;
import com.cloudloan.bootcamp.homework.h01.gateway.filter.HttpResponseFilter;
import com.cloudloan.bootcamp.homework.h01.gateway.outbound.AbstractHttpOutboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 后端服务请求发起实现方式 - apache http client
 *
 * @author zhaochen
 */
@Slf4j
public class ApacheHttpOutboundHandler extends AbstractHttpOutboundHandler {

    private final CloseableHttpAsyncClient httpclient;
    private final HttpResponseFilter responseFilter = new HeaderHttpResponseFilter();

    public ApacheHttpOutboundHandler(List<String> backends) {
        super(backends);
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setRcvBufSize(32 * 1024)
                .build();
        this.httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        this.httpclient.start();
    }

    @Override
    public void doHandle(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpGet.setHeader("mao", inbound.headers().get("mao"));

        this.httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                try {
                    handleResponse(inbound, ctx, endpointResponse);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

            @Override
            public void failed(final Exception ex) {
                httpGet.abort();
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    private void handleResponse(final FullHttpRequest fullRequest,
                                final ChannelHandlerContext ctx,
                                final HttpResponse endpointResponse) {
        FullHttpResponse fullHttpResponse = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
            responseFilter.filter(fullHttpResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
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

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

}
