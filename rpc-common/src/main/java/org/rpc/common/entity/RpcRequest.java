package org.rpc.common.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * rpc请求参数
 *
 * @author wangtongzhou 
 * @since 2022-01-02 08:47
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private Object[] parameters;

    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;

}
