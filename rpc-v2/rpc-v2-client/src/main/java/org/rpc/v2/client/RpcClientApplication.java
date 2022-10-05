package org.rpc.v2.client;

import lombok.extern.slf4j.Slf4j;
import org.rpc.api.HelloService;
import org.rpc.api.User;
import org.rpc.api.UserService;
import org.rpc.v2.client.utils.RpcClientConfig;
import org.rpc.v2.client.utils.RpcClientProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * rpc client
 *
 * @author wangtongzhou
 * @since 2022-01-02 10:38
 */
@Slf4j
public class RpcClientApplication {

    public static void main(String[] args) {
        ApplicationContext context=new
                AnnotationConfigApplicationContext(RpcClientConfig.class);
        RpcClientProxy rpcClientProxy=context.getBean(RpcClientProxy.class);
        UserService userService = rpcClientProxy.getProxy(UserService.class, "127" +
                        ".0.0.1",
                8888);
        User result = userService.queryUserInfo();
        log.info(result.toString());
    }
}
