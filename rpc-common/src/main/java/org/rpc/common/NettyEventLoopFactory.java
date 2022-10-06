package org.rpc.common;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * NettyEventLoop工具类
 *
 * @author wangtongzhou
 * @since 2022-10-05 20:01
 */
public class NettyEventLoopFactory {

    public static EventLoopGroup eventLoopGroup(int threads, String threadFactoryName) {
        ThreadFactory threadFactory = new DefaultThreadFactory(threadFactoryName, true);
        return shouldEpoll() ? new EpollEventLoopGroup(threads, threadFactory) :
                new NioEventLoopGroup(threads, threadFactory);
    }

    private static boolean shouldEpoll() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
}
