package com.cloudloan.bootcamp.homework.h01.gateway.router;

import java.util.List;

/**
 * 路由
 *
 * @author zhaochen
 */
public interface HttpEndpointRouter {

    /**
     * 执行路由
     *
     * @param endpoints 节点列表
     * @return 路由节点
     */
    String route(List<String> endpoints);

    // Load Balance
    // Random
    // RoundRibbon 
    // Weight
    // - server01,20
    // - server02,30
    // - server03,50

}
