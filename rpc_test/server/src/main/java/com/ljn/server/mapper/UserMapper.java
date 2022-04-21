package com.ljn.server.mapper;

import com.ljn.server.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserById(String id);
}
