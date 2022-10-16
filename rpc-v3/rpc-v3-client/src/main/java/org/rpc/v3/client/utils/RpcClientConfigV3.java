package org.rpc.v3.client.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加载rpc client proxy
 *
 * @author wangtongzhou
 * @since 2022-10-05 16:46
 */
@Configuration
public class RpcClientConfigV3 {

    @Bean
    public RpcClientProxy rpcClientProxy() {
        return new RpcClientProxy();
    }
}
