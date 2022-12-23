package com.example.springbootvue3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 丁祥 QQ 2421341497
 * @since 2022-11-15
 */
@Getter
@Setter
@TableName("goods_category")
public class GoodsCategory {

      @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    private Integer pid;

    private String name;
    @TableField(exist = false)
    private List<GoodsCategory> children = new ArrayList<>();
}
