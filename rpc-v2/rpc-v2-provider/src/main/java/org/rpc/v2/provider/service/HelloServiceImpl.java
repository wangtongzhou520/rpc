package org.rpc.v2.provider.service;

import org.rpc.api.HelloService;
import org.rpc.v2.provider.utils.RpcService;

/**
 * hello
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:08
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "hello";
    }
}
