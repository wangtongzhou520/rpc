package org.rpc.v3.provider;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 初始化 channel
 *
 * @author wangtongzhou 18635604249
 * @since 2022-10-06 09:38
 */
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //                                    ch.pipeline().addLast("demp-rpc-decoder", new DemoRpcDecoder());
//                                    ch.pipeline().addLast("demo-rpc-encoder", new DemoRpcEncoder());
//                                    ch.pipeline().addLast("server-handler", new DemoRpcServerHandler());
    }
}
