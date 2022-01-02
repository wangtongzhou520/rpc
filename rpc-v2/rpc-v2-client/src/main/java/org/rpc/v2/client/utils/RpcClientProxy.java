package org.rpc.v2.client.utils;

import java.lang.reflect.Proxy;

/**
 * client 代理对象
 *
 * @author wangtongzhou
 * @since 2022-01-02 10:07
 */
public class RpcClientProxy {

    public <T> T getProxy(Class<T> interfaceClass, String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RemoteInvocationHandler(host, port));
    }
}
