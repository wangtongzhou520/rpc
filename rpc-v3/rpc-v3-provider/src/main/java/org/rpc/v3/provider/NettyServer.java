package org.rpc.v3.provider;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.rpc.common.netty.NettyEventLoopFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 实现服务提供者将注册信息添加到一个容器中
 *
 * @author wangtongzhou
 * @since 2022-01-08 19:05
 */
@Slf4j
@Component
public class NettyServer {

    private final EventLoopGroup bossGroup = NettyEventLoopFactory.eventLoopGroup(1,
            "netty-server-boss");
    private final EventLoopGroup workerGroup =
            NettyEventLoopFactory.eventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                    "netty-server-worker");
    private Channel channel;

    /**
     * 绑定
     *
     * @param address
     * @return
     */
    public ChannelFuture bind(InetSocketAddress address) {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
//                    //指定每个Channel上注册的ChannelHandler以及顺序
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyChannelInitializer());
            channelFuture = serverBootstrap.bind(address).syncUninterruptibly();
            channel = channelFuture.channel();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                log.info("启动成功");
            } else {
                log.error("启动失败");
            }
        }
        return channelFuture;
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (null == channel) {
            return;
        }
        channel.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }

}
