package com.zack.mapper;

import com.zack.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int checkUserName(String username);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    int checkEmail(String str);
}