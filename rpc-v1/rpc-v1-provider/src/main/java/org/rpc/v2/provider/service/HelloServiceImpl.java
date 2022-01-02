package org.rpc.v2.provider.service;

import org.rpc.api.HelloService;

/**
 * hello
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:08
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "hello";
    }
}
