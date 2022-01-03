package org.rpc.v2.provider.utils;

import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动扫描 RpcService 注解
 *
 * @author wangtongzhou 
 * @since 2022-01-03 21:23
 */
@Configuration
@ComponentScan(value = "org.rpc.v2.provider.service")
public class RpcServiceConfig {

    @Bean
    public RpcProxyService rpcProxyService() {
        return new RpcProxyService(8888);
    }
}
