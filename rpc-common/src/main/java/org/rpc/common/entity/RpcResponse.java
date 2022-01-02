package org.rpc.common.entity;

import lombok.Data;
import org.rpc.common.enums.ResponseCode;

import java.io.Serializable;

/**
 * rpc返回
 *
 * @author wangtongzhou 
 * @since 2022-01-02 09:01
 */
@Data
public class RpcResponse<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提醒信息
     */
    private String message;

    /**
     * 返回信息
     */
    private T data;


    public static <T> RpcResponse<T> ok(T data) {
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(ResponseCode.SUCCESS.getCode());
        rpcResponse.setData(data);
        rpcResponse.setMessage(rpcResponse.getMessage());
        return rpcResponse;
    }

    public static <T> RpcResponse<T> error(int code, String message) {
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(code);
        rpcResponse.setMessage(message);
        return rpcResponse;
    }

}
