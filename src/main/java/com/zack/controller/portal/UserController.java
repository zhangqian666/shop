package com.zack.controller.portal;


import com.zack.common.Const;
import com.zack.common.ServerResponse;
import com.zack.model.User;
import com.zack.service.IUserService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> userServerResponse = iUserService.login(username, password);
        if (userServerResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, userServerResponse.getData());
        }
        return userServerResponse;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession httpSession) {
        httpSession.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_vaild", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession httpSession, String passwordOld, String passwordNew) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }


}
