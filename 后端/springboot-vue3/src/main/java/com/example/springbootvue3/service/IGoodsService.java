package com.example.springbootvue3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootvue3.entity.Goods;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 丁祥 QQ 2421341497
 * @since 2022-11-14
 */
public interface IGoodsService extends IService<Goods> {
    List<Map<String,Object>> goodsList(int limit1, int limit2);
}
