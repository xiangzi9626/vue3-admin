package com.example.springbootvue3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author 丁祥
 * @since 2022-10-06
 */
@Getter
@Setter
@TableName("system_menu")
public class SystemMenu {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父ID
     */
    private Integer pid;

    /**
     * 名称
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 链接
     */
    private String path;
    /**
     * 菜单排序
     */
    private Integer sort;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Byte status;


    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;


    private String component;
    private String url;
    @TableField(exist = false)
    private List<SystemMenu> children = new ArrayList<>();
}
