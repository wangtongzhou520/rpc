package org.rpc.v2.provider.service;

import org.rpc.api.User;
import org.rpc.api.UserService;
import org.rpc.v2.provider.utils.RpcService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * user
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:10
 */
@RpcService(UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public User queryUserInfo() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("哈哈");
        user.setAge(7);
        return user;
    }
}
