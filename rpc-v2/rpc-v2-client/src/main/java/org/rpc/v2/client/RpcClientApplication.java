package org.rpc.v2.client;

import lombok.extern.slf4j.Slf4j;
import org.rpc.api.HelloService;
import org.rpc.v2.client.utils.RpcClientProxy;

/**
 * rpc client
 *
 * @author wangtongzhou
 * @since 2022-01-02 10:38
 */
@Slf4j
public class RpcClientApplication {

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy();
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class, "127.0.0.1",
                8888);
        String result = helloService.sayHello();
        log.info(result);
    }
}
