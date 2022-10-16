package org.rpc.common.netty;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;
import org.rpc.common.entity.RpcRequest;

/**
 * Netty 处理异步回调
 *
 * @author wangtongzhou
 * @since 2022-10-08 07:35
 */
public class NettyResponseFuture<T> {

    private long createTime;
    private long timeOut;
    private RpcRequest request;
    private Channel channel;
    private Promise<T> promise;

    public NettyResponseFuture(long createTime, long timeOut, RpcRequest request, Channel channel, Promise<T> promise){
        this.createTime = createTime;
        this.timeOut = timeOut;
        this.request = request;
        this.channel = channel;
        this.promise = promise;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public RpcRequest getRequest() {
        return request;
    }

    public void setRequest(RpcRequest request) {
        this.request = request;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Promise<T> getPromise() {
        return promise;
    }

    public void setPromise(Promise<T> promise) {
        this.promise = promise;
    }
}
