package com.example.springbootvue3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvue3.entity.SystemMenu;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author 丁祥
 * @since 2022-10-06
 */
public interface ISystemMenuService extends IService<SystemMenu> {
     List<SystemMenu> buildTreeMenu(List<SystemMenu> sysMenuList);
}
