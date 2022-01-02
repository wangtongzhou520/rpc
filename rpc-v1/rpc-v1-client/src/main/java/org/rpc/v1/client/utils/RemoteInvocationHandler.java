package org.rpc.v1.client.utils;

import org.rpc.common.entity.RpcRequest;
import org.rpc.common.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 执行远程调用
 *
 * @author wangtongzhou
 * @since 2022-01-02 10:17
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;

    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构造请求参数
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        //发送请求
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse) rpcClient.send(rpcRequest, host, port)).getData();
    }
}
