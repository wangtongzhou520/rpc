package org.rpc.v3.provider;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.rpc.common.entity.RpcRequest;
import org.rpc.common.entity.RpcResponse;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射执行实际方法调用
 *
 * @author wangtongzhou
 * @since 2022-01-02 08:26
 */
@Slf4j
public class ProcessorHandler implements Runnable {

    private ChannelHandlerContext channelHandlerContext;

    private RpcRequest rpcRequest;

    public ProcessorHandler(ChannelHandlerContext channelHandlerContext,
                            RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void run() {

        RpcResponse response = new RpcResponse();
        //从参数中获取类名
        String serviceName = rpcRequest.getInterfaceName();
        //判断该类是否在服务端实现
        Object service = SpringBootBeanUtil.getBean(serviceName);
        if (ObjectUtils.isEmpty(service)) {
            throw new RuntimeException("服务未注册");
        }
        try {
            //通过反射获取到方法
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            //执行方法
            Object result = method.invoke(service, rpcRequest.getParameters());
            response.setMessageId(rpcRequest.getMessageId());
            response.setData(result);
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            //此处可再次进行包装将异常情况分类
            log.error("调用时发生错误", exception);
        }
        channelHandlerContext.writeAndFlush(response);
    }
}
