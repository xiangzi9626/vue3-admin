package com.example.springbootvue3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvue3.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 丁祥
 * @since 2022-10-04
 */
public interface IUserService extends IService<User> {
List<Map<String,Object>> adminList(int level,int limit1,int limit2);
List<User> userList(int level, int limit1, int limit2);
}
