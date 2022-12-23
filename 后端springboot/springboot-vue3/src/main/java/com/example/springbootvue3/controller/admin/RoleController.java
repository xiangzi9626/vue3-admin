package com.example.springbootvue3.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springbootvue3.entity.Result;
import com.example.springbootvue3.entity.Role;
import com.example.springbootvue3.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 丁祥 QQ 2421341497
 * @since 2022-12-02
 */
@RestController
@RequestMapping("/admin")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping("/rolelist")
    public List<Role> roleList() {
        List<Role> roleList = roleService.list();
        return roleList;
    }

    @RequestMapping("/add_role")
    public Map<String, Object> addRole(@RequestBody Role role) {
        boolean save = roleService.save(role);
        if (save) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败请重试");
        }
    }

    @RequestMapping("/delete_role")
    public Map<String, Object> deleteRole(@RequestParam Integer id) {
        boolean remove = roleService.remove(new QueryWrapper<Role>().eq("role_id", id));
        if (remove) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败请重试");
        }
    }

    @RequestMapping("/edit_role")
    public Map<String, Object> editRole(@RequestBody Role role) {
        boolean update = roleService.update(new UpdateWrapper<Role>().set("role_name", role.getRoleName()).eq("role_id", role.getRoleId()));
        if (update) {
            return Result.success("提交成功");
        } else {
            return Result.error("提交失败请重试");
        }
    }

    @RequestMapping("/role_menus")
    public Map<String, Object> roleMenus(@RequestParam("role_id") Integer roleId) {
        Role role = roleService.getById(roleId);
        return Result.res(role);
    }

    @RequestMapping("/edit_power")
    public Map<String, Object> editPower(@RequestParam("role_id") Integer roleId,@RequestParam("powerMenus") String powerMenus) {
        boolean update = roleService.update(new UpdateWrapper<Role>().set("power_menus",powerMenus).eq("role_id",roleId));
        if (update) {
            return Result.success("提交成功");
        }
        return Result.error("提交失败请重试");
    }
}
