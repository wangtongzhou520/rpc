package org.rpc.provider.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 暴露rpc服务
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:21
 */
@Slf4j
public class RpcProxyService {

    private ExecutorService threadPool;

    public RpcProxyService() {
        int corePoolSize = 5;
        int maximumPoolSize = 200;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("socket-pool-").build();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    /**
     * 暴露方法，在注册完成以后服务后立刻开始监听
     *
     * @param service
     * @param port
     */
    public void register(Object service, int port) {

        try (ServerSocket serverSocket = new ServerSocket(port);) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("客户端连接IP为：" + socket.getInetAddress());
                threadPool.execute(new ProcessorHandler(socket, service));
            }
        } catch (IOException e) {
            log.error("连接异常", e);
        }
    }
}
