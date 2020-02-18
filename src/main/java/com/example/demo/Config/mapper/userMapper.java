package com.example.demo.Config.mapper;

import com.example.demo.daoPOJO.User;
import org.apache.ibatis.annotations.Param;

public interface userMapper {
    void insertUser(User user);

    User findUserByToken(@Param("token") String token);

    User findById(Integer id);
}
