package com.cloudloan.bootcamp.homework.h01.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * {@link HttpEndpointRouter} 实现 - 随机
 *
 * @author zhaochen
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }

}
