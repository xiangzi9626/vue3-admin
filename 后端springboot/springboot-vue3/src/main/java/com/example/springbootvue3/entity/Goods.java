package com.example.springbootvue3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 丁祥 QQ 2421341497
 * @since 2022-11-14
 */
@Getter
@Setter
@TableName("goods")
public class Goods {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String cid;

    private String title;

    private String content;

    private BigDecimal price;

    /**
     * 商品主图
     */
    private String img;
    private Integer stock;
    private Byte status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
