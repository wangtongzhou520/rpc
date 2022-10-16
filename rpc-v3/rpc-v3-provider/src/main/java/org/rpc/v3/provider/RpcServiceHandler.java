package org.rpc.v3.provider;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.collections4.MapUtils;
import org.rpc.common.entity.RpcRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * RpcServiceHandler
 *
 * @author wangtongzhou
 * @since 2022-10-12 06:51
 */
public class RpcServiceHandler extends ChannelInboundHandlerAdapter {

    private ExecutorService threadPool;



    public RpcServiceHandler(){
        int corePoolSize = 5;
        int maximumPoolSize = 200;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("socket-pool-").build();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) obj;
        threadPool.execute(new ProcessorHandler(ctx, rpcRequest));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
