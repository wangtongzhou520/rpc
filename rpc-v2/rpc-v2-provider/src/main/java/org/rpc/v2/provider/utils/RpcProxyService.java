package org.rpc.v2.provider.utils;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 暴露rpc服务
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:21
 */
@Slf4j
public class RpcProxyService implements ApplicationContextAware, InitializingBean {

    private ExecutorService threadPool;

    private int port;

    /**
     * 服务容器
     */
    private HashMap<String, Object> serviceMap = Maps.newHashMap();

    public RpcProxyService(int port) {
        this.port = port;
        int corePoolSize = 5;
        int maximumPoolSize = 200;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("socket-pool-").build();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("客户端连接IP为：" + socket.getInetAddress());
                threadPool.execute(new ProcessorHandler(socket, serviceMap));
            }
        } catch (IOException e) {
            log.error("连接异常", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            serviceBeanMap.values().forEach(x -> {
                RpcService service = x.getClass().getAnnotation(RpcService.class);
                String serviceName = service.value().getName();
                serviceMap.put(serviceName, x);
            });
        }
    }
}
