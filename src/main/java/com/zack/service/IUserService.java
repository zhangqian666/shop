package com.zack.service;

import com.zack.common.ServerResponse;
import com.zack.model.User;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User currentUser);

    ServerResponse<User> getInformation(Integer id);

    ServerResponse<String> selectQuestion(Integer uid);

    ServerResponse<String> checkAnswer(Integer uid, String question, String answer);

    ServerResponse<String> forgetResetPassword(Integer uid, String passwordNew, String forgetToken);

    ServerResponse checkAdminRole(User user);
}
