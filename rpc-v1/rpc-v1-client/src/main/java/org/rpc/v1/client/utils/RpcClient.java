package org.rpc.v1.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.rpc.common.entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * client序列化
 *
 * @author wangtongzhou
 * @since 2022-01-02 09:50
 */
@Slf4j
public class RpcClient {

    public Object send(RpcRequest rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            //序列化
            outputStream.writeObject(rpcRequest);
            outputStream.flush();
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用时发生异常", e);
            return null;
        }
    }
}
