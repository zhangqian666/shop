package com.zack.service;

import com.zack.common.ServerResponse;
import com.zack.model.User;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);
}
