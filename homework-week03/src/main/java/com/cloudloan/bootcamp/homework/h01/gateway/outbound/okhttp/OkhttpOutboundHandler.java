package com.cloudloan.bootcamp.homework.h01.gateway.outbound.okhttp;

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
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 后端服务请求发起实现方式 - okHttp
 *
 * @author zhaochen
 */
@Slf4j
public class OkhttpOutboundHandler extends AbstractHttpOutboundHandler {

    private final OkHttpClient okHttpClient;
    private final HttpResponseFilter responseFilter = new HeaderHttpResponseFilter();

    public OkhttpOutboundHandler(List<String> backendUrls) {
        super(backendUrls);
        this.okHttpClient = new OkHttpClient();
    }

    @Override
    public void doHandle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String targetUrl) {
        FullHttpResponse fullHttpResponse = null;
        Request request = new Request.Builder().url(targetUrl).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            byte[] body = response.body().bytes();
            final String bodyLength = response.header("Content-Length");
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", Integer.parseInt(bodyLength));
            responseFilter.filter(fullHttpResponse);
        } catch (Exception e) {
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
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
