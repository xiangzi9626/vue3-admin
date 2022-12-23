package com.example.springbootvue3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootvue3.entity.User;
import com.example.springbootvue3.mapper.UserMapper;
import com.example.springbootvue3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 丁祥
 * @since 2022-10-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Map<String,Object>> adminList(int level, int limit1, int limit2) {
        return userMapper.adminList(level, limit1, limit2);
    }

    @Override
    public List<User> userList(int level, int limit1, int limit2) {
        return userMapper.userList(level,limit1,limit2);
    }
}
