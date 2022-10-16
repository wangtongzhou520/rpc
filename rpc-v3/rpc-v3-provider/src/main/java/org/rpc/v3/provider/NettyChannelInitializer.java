package org.rpc.v3.provider;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.rpc.common.entity.RpcRequest;
import org.rpc.common.entity.RpcResponse;
import org.rpc.common.netty.RpcDecoder;
import org.rpc.common.netty.RpcEncoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 初始化 channel
 *
 * @author wangtongzhou
 * @since 2022-10-06 09:38
 */
@Component
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast( new RpcDecoder(RpcRequest.class));
        socketChannel.pipeline().addLast(new RpcEncoder(RpcResponse.class));
        socketChannel.pipeline().addLast(new RpcServiceHandler());
    }

}
