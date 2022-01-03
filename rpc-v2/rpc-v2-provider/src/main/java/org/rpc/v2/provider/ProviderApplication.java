package org.rpc.v2.provider;

import org.rpc.api.HelloService;
import org.rpc.v2.provider.service.HelloServiceImpl;
import org.rpc.v2.provider.utils.RpcProxyService;
import org.rpc.v2.provider.utils.RpcServiceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 服务提供者
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:07
 */
public class ProviderApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(RpcServiceConfig.class);
        context.start();
    }
}
