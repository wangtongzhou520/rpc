package org.rpc.v2.provider.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.rpc.common.entity.RpcRequest;
import org.rpc.common.entity.RpcResponse;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射执行实际方法调用
 *
 * @author wangtongzhou
 * @since 2022-01-02 08:26
 */
@Slf4j
public class ProcessorHandler implements Runnable {

    private Socket socket;

    private Map<String, Object> serviceMap;

    public ProcessorHandler(Socket socket, Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
        this.socket = socket;
    }

    @Override
    public void run() {

        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            //从输入流读取参数
            RpcRequest rpcRequest = (RpcRequest) inputStream.readObject();
            //从参数中获取类名
            String serviceName = rpcRequest.getInterfaceName();
            //判断该类是否在服务端实现
            Object service = serviceMap.get(serviceName);
            if (ObjectUtils.isEmpty(service)) {
                throw new RuntimeException("服务为注册");
            }
            //通过反射获取到方法
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            //执行方法
            Object result = method.invoke(service, rpcRequest.getParameters());
            outputStream.writeObject(RpcResponse.ok(result));
            outputStream.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            //此处可再次进行包装将异常情况分类
            log.error("调用时发生错误", exception);
        }

    }
}
