package org.rpc.v2.client.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加载rpc client proxy
 *
 * @author wangtongzhou 18635604249
 * @since 2022-10-05 16:46
 */
@Configuration
public class RpcClientConfig {

    @Bean
    public RpcClientProxy rpcClientProxy() {
        return new RpcClientProxy();
    }
}
