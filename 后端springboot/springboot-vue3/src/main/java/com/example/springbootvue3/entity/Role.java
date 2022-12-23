package com.example.springbootvue3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author 丁祥 QQ 2421341497
 * @since 2022-12-02
 */
@Getter
@Setter
@TableName("role")
public class Role {

    /**
     * 角色主键ID
     */
      @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;
    private String powerMenus;
}
