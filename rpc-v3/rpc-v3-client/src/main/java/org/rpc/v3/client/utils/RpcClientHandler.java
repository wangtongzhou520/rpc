package org.rpc.v3.client.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.rpc.common.entity.RpcResponse;
import org.rpc.common.netty.Connection;
import org.rpc.common.netty.NettyResponseFuture;
import org.springframework.stereotype.Component;

/**
 * RpcClientHandler
 *
 * @author wangtongzhou
 * @since 2022-10-12 20:55
 */
@Component
public class RpcClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {

        RpcResponse rpcResponse = (RpcResponse) obj;

        NettyResponseFuture responseFuture =
                Connection.IN_FLIGHT_REQUEST_MAP.remove(rpcResponse.getMessageId());
        responseFuture.getPromise().setSuccess(rpcResponse.getData());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
