package com.cloudloan.bootcamp.homework.h01.gateway.outbound;

import com.cloudloan.bootcamp.homework.h01.gateway.filter.HttpRequestFilter;
import com.cloudloan.bootcamp.homework.h01.gateway.router.HttpEndpointRouter;
import com.cloudloan.bootcamp.homework.h01.gateway.router.RandomHttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * {@link HttpOutboundHandler} 抽象实现，完成以下职责
 * <p>
 * 封装线程池
 *
 * @author zhaochen
 */
@Slf4j
public abstract class AbstractHttpOutboundHandler implements HttpOutboundHandler {

    private final List<String> backendUrls;
    private final ExecutorService proxyService;
    private final HttpEndpointRouter router;

    public AbstractHttpOutboundHandler(List<String> backendUrls) {
        // 格式化代理的后端服务列表
        this.backendUrls = backendUrls.stream()
                .map(backendUrl -> backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl)
                .collect(Collectors.toList());

        // 初始化发起外部请求的线程池
        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        this.proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        // 初始化后端服务路由器
        this.router = new RandomHttpEndpointRouter();
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, List<HttpRequestFilter> requestFilterList) {
        // 路由代理的后端服务列表，根据路由规则选择出目标请求地址
        final String backendUrl = this.router.route(this.backendUrls);
        final String targetUrl = backendUrl + fullRequest.uri();
        // 调用请求过滤器过滤发起的请求
        requestFilterList.forEach(filter -> filter.filter(fullRequest, ctx));
        this.proxyService.submit(() -> doHandle(fullRequest, ctx, targetUrl));
    }

    /**
     * 发起实际请求
     * <p>
     * 子类负责发起具体的请求，如采用 httpclient、okHttp、netty client
     *
     * @param fullRequest {@link FullHttpRequest}
     * @param ctx         {@link ChannelHandlerContext}
     * @param targetUrl   请求目标
     */
    public abstract void doHandle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String targetUrl);

}
