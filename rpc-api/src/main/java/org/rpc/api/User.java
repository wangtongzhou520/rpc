package org.rpc.api;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:02
 */
public class User implements Serializable {

    private Long userId;

    private String userName;

    private Integer age;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
