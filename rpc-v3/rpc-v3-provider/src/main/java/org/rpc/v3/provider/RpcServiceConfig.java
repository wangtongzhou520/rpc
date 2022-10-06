package org.rpc.v3.provider;

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
@ComponentScan(value = "org.rpc.v3.service")
public class RpcServiceConfig {

    @Bean
    public NettyServer nettyServer() {
        return new NettyServer();
    }
}
