package com.example.springbootvue3.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootvue3.entity.Result;
import com.example.springbootvue3.entity.Role;
import com.example.springbootvue3.entity.SystemMenu;
import com.example.springbootvue3.entity.User;
import com.example.springbootvue3.service.impl.RoleServiceImpl;
import com.example.springbootvue3.service.impl.SystemMenuServiceImpl;
import com.example.springbootvue3.service.impl.UserServiceImpl;
import com.example.springbootvue3.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author 丁祥
 * @since 2022-10-06
 */
@RestController
@RequestMapping("/admin/menu")
public class SystemMenuController {
    @Autowired
    private SystemMenuServiceImpl systemMenuService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleServiceImpl roleService;
    @RequestMapping("/all")
    public List<SystemMenu> menuAll(@RequestHeader(value = "token") String token) {
        String username = JwtUtils.getMemberIdByJwtToken(token);
        List<SystemMenu> menuList = systemMenuService.list(new QueryWrapper<SystemMenu>().orderByAsc("sort"));
        List<SystemMenu> treeMenuList = systemMenuService.buildTreeMenu(menuList);
        return treeMenuList;
    }

    @RequestMapping("/list")
    public List<SystemMenu> menu(@RequestHeader(value = "token") String token) {
        String username = JwtUtils.getMemberIdByJwtToken(token);
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        String roleId = user.getRId();
        Role role = roleService.getOne(new QueryWrapper<Role>().eq("role_id", roleId));
        String[] arr = role.getPowerMenus().split(",");
        List<SystemMenu> menuList = systemMenuService.list(new QueryWrapper<SystemMenu>().in("id", arr).orderByAsc("sort"));
        List<SystemMenu> treeMenuList = systemMenuService.buildTreeMenu(menuList);
        return treeMenuList;
    }

    @RequestMapping("/delete")
    public Map<String, Object> deleteMenu(@RequestBody Map<String, Integer> param) {
        int id = param.get("id");
        Long menuCount = systemMenuService.count(new QueryWrapper<SystemMenu>().eq("pid", id));
        if (menuCount > 0) {
            return Result.error("请先删除子菜单");
        }
        boolean del = systemMenuService.remove(new QueryWrapper<SystemMenu>().eq("id", id));
        if (del) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败请重试");
        }
    }

    @RequestMapping("/add")
    public Map<String, Object> addMenu(@RequestBody SystemMenu systemMenu) {
        Boolean save = systemMenuService.save(systemMenu);
        if (save) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败请重试");
        }
    }

    @RequestMapping("/get")
    public SystemMenu getMenuById(@RequestParam Integer id) {
        SystemMenu systemMenu = systemMenuService.getById(id);
        return systemMenu;
    }

    @RequestMapping("/edit")
    public Map<String, Object> updateMenu(@RequestBody SystemMenu systemMenu) {
        boolean update = systemMenuService.updateById(systemMenu);
        if (update) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败请重试");
        }
    }
}
