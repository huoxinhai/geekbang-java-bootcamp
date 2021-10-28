package com.cloudloan.bootcamp.homework.h01.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * {@link HttpResponseFilter} 实现
 *
 * @author zhaochen
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse response) {
        // http header 中添加自定义信息
        response.headers().add("geekbang-java-bootcamp-res", "zhaochen add response header");
    }

}
