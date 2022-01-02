package org.rpc.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回值枚举
 *
 * @author wangtongzhou 
 * @since 2022-01-02 09:14
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "成功"),
    FAIL(500, "服务器异常");

    private int code;

    private String message;

}
