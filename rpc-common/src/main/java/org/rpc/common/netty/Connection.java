package org.rpc.common.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import org.rpc.common.entity.RpcRequest;
import org.rpc.common.entity.RpcResponse;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 连接
 *
 * @author wangtongzhou
 * @since 2022-10-10 07:40
 */
public class Connection implements Closeable {

    /**
     * 生成消息ID 全局唯一 分布式场景下可以使用雪花算法等代替
     */
    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    /**
     * 存储消息与Future的关系
     */
    public final static Map<Long, NettyResponseFuture<RpcResponse>> IN_FLIGHT_REQUEST_MAP
            = new ConcurrentHashMap<>();


    private ChannelFuture future;

    private AtomicBoolean isConnected = new AtomicBoolean();

    public Connection() {
        this.isConnected.set(false);
        this.future = null;
    }

    public Connection(ChannelFuture future, boolean isConnected) {
        this.future = future;
        this.isConnected.set(isConnected);
    }


    public NettyResponseFuture<RpcResponse> send(RpcRequest request,
                                          long timeOut) {
        Long messageId=ID_GENERATOR.incrementAndGet();
        request.setMessageId(messageId);
        // 创建消息关联的Future
        NettyResponseFuture responseFuture = new NettyResponseFuture(System.currentTimeMillis(),
                timeOut, request, future.channel(), new DefaultPromise(new DefaultEventLoop()));
        IN_FLIGHT_REQUEST_MAP.put(messageId, responseFuture);
        try {
            //发送请求
            future.channel().writeAndFlush(request);
        } catch (Exception e) {
            throw e;
        }
        return responseFuture;
    }

    public ChannelFuture getFuture() {
        return future;
    }

    public void setFuture(ChannelFuture future) {
        this.future = future;
    }

    public AtomicBoolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(AtomicBoolean isConnected) {
        this.isConnected = isConnected;
    }




    @Override
    public void close() throws IOException {
        future.channel().close();
    }
}
