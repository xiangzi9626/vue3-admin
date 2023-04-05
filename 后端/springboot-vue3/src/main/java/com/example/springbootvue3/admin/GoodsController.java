package com.example.springbootvue3.admin;

import com.example.springbootvue3.entity.Goods;
import com.example.springbootvue3.entity.Result;
import com.example.springbootvue3.service.impl.GoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 丁祥 QQ 2421341497
 * @since 2022-11-14
 */
@RestController
@RequestMapping("/admin/goods")
public class GoodsController {
    @Autowired
    private GoodsServiceImpl goodsService;

    @RequestMapping("/batch_delete")
    public Map<String, Object> batchDelete(@RequestBody List<Integer> ids) {
        boolean batch = goodsService.removeBatchByIds(ids);
        if (batch) {
            return Result.success("批量删除成功");
        } else {
            return Result.error("删除失败请重试");
        }
    }

    @RequestMapping("/get")
    public Goods getGoodsById(@RequestParam Integer id) {
        Goods goods = goodsService.getById(id);
        return goods;
    }

    @RequestMapping("/add")
    public Map<String, Object> addGoods(@RequestBody Goods goods) {
        boolean save = goodsService.save(goods);
        if (save) {
            return Result.success("");
        } else {
            return Result.error("提交失败请重试");
        }
    }

    @RequestMapping("/edit")
    public Map<String, Object> updateGoods(@RequestBody Goods goods) {
        boolean update = goodsService.updateById(goods);
        if (update) {
            return Result.success("更新成功");
        } else {
            return Result.error("提交失败请重试");
        }
    }

    @RequestMapping("/list")
    public Map<String, Object> goodsList(@RequestBody Map<String, Integer> param) {
        int currentPage = param.get("currentPage");
        int pageSize = param.get("pageSize");
        long total = goodsService.count();
        List<Map<String,Object>> goodsList = goodsService.goodsList((currentPage - 1) * pageSize, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("data", goodsList);
        return map;
    }


    @RequestMapping("/delete")
    public Map<String, Object> deleteGoods(@RequestBody Map<String, Integer> param) {
        int id = param.get("id");
        boolean remove = goodsService.removeById(id);
        if (remove) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败请重试");
        }
    }
}
