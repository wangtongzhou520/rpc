package org.rpc.v3.client.utils;

import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.rpc.common.entity.RpcRequest;
import org.rpc.common.netty.Connection;
import org.rpc.common.netty.NettyResponseFuture;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 执行远程调用
 *
 * @author wangtongzhou
 * @since 2022-01-02 10:17
 */
@Slf4j
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
        try {
            RpcClient rpcClient = new RpcClient(host, port);
            ChannelFuture channelFuture = rpcClient.connect().awaitUninterruptibly();
            Connection connection = new Connection(channelFuture, true);
            NettyResponseFuture responseFuture = connection.send(rpcRequest,5);
            return responseFuture.getPromise().get(60, TimeUnit.SECONDS);
        }catch (Exception ex){
            throw ex;
        }

    }
}
