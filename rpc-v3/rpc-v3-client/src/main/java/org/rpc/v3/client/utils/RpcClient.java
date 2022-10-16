package org.rpc.v3.client.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.rpc.common.entity.RpcResponse;
import org.rpc.common.netty.NettyEventLoopFactory;
import org.rpc.common.entity.RpcRequest;
import org.rpc.common.netty.RpcDecoder;
import org.rpc.common.netty.RpcEncoder;

import java.io.Closeable;
import java.io.IOException;

/**
 * client序列化
 *
 * @author wangtongzhou
 * @since 2022-01-02 09:50
 */
@Slf4j
public class RpcClient implements Closeable {

    protected Bootstrap bootstrap;
    protected EventLoopGroup eventLoopGroup;
    private ChannelFuture channelFuture;
    private String host;
    private int port;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
        // 创建并配置客户端Bootstrap
        bootstrap = new Bootstrap();
        eventLoopGroup = NettyEventLoopFactory.eventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                "netty-client-worker");
        bootstrap.group(eventLoopGroup)
                .option(ChannelOption.AUTO_READ, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new RpcDecoder(RpcResponse.class));
                        ch.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                        ch.pipeline().addLast(new RpcClientHandler());
                    }
                });
    }

    /**
     * 建立连接
     *
     * @return
     */
    public ChannelFuture connect() {
        // 连接指定的地址和端口
        ChannelFuture connect = bootstrap.connect(host, port);
        connect.awaitUninterruptibly();
        return connect;
    }



    @Override
    public void close() throws IOException {
        eventLoopGroup.shutdownGracefully();

    }
}
