package org.rpc.v3;

import io.netty.channel.ChannelFuture;
import org.rpc.v3.provider.NettyServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.InetSocketAddress;

/**
 * 服务提供者
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:07
 */
public class ProviderApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(NettyServer.class);
        NettyServer nettyServer= (NettyServer) context.getBean("nettyServer");
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
        ChannelFuture channelFuture = nettyServer.bind(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
