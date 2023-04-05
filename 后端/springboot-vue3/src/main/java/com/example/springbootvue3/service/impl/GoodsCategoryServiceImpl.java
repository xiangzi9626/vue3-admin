package com.example.springbootvue3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootvue3.entity.Goods;
import com.example.springbootvue3.entity.GoodsCategory;
import com.example.springbootvue3.mapper.GoodsCategoryMapper;
import com.example.springbootvue3.mapper.GoodsMapper;
import com.example.springbootvue3.service.IGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 丁祥 QQ 2421341497
 * @since 2022-11-15
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements IGoodsCategoryService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    private Goods goods;

    @Transactional
    public void removeAndUpdate(Integer id) {
        goodsCategoryMapper.delete(new QueryWrapper<GoodsCategory>().eq("category_id", id));
        goodsMapper.update(goods, new UpdateWrapper<Goods>().set("cid", 0).eq("cid", id));
    }
}
