package com.cloudloan.bootcamp.homework.h01.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * http 响应过滤器
 *
 * @author zhaochen
 */
public interface HttpResponseFilter {

    /**
     * 过滤响应
     *
     * @param response {@link FullHttpResponse}
     */
    void filter(FullHttpResponse response);

}
