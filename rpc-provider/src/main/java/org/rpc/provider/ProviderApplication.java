package org.rpc.provider;

import org.rpc.api.HelloService;
import org.rpc.provider.service.HelloServiceImpl;
import org.rpc.provider.utils.RpcProxyService;

/**
 * 服务提供者
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:07
 */
public class ProviderApplication {

    public static void main(String[] args) {

        RpcProxyService rpcProxyService = new RpcProxyService();
        HelloService helloService = new HelloServiceImpl();
        rpcProxyService.register(helloService,8888);
    }
}
