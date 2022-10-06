package org.rpc.v3.service;

import org.rpc.api.HelloService;
import org.rpc.v3.provider.RpcService;

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
