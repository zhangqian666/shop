package com.zack.service.ipml;

import com.zack.common.Const;
import com.zack.common.ServerResponse;
import com.zack.mapper.UserMapper;
import com.zack.model.User;
import com.zack.service.IUserService;
import com.zack.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.invoke.empty.Empty;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String md5EncodeUtf8 = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5EncodeUtf8);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);

    }

    public ServerResponse<String> register(User user) {
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {

        int checkPassword = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (checkPassword == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateByPrimaryKey = userMapper.updateByPrimaryKeySelective(user);
        if (updateByPrimaryKey > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");

    }

    @Override
    public ServerResponse<User> updateInformation(User currentUser) {
        int checkEmailByUserId = userMapper.checkEmailByUserId(currentUser.getEmail(), currentUser.getId());
        if (checkEmailByUserId > 0) {
            return ServerResponse.createByErrorMessage("Email已存在，请更换Email再进行更新");
        }
        User updateUser = new User();
        updateUser.setId(currentUser.getId());
        updateUser.setEmail(currentUser.getEmail());
        updateUser.setPhone(currentUser.getPhone());
        updateUser.setQuestion(currentUser.getQuestion());
        updateUser.setAnswer(currentUser.getAnswer());
        int updateByPrimaryKeySelective = userMapper.updateByPrimaryKeySelective(updateUser);
        System.err.println("update code : " + updateByPrimaryKeySelective);
        if (updateByPrimaryKeySelective > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword("");
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse<String> stringServerResponse = this.checkValid(username, Const.USERNAME);
        if (stringServerResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        return null;
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        return null;
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return null;
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotEmpty(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUserName(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }
}
