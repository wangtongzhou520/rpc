package org.rpc.v1.provider.service;

import org.rpc.api.User;
import org.rpc.api.UserService;

/**
 * user
 *
 * @author wangtongzhou
 * @since 2022-01-01 16:10
 */
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
