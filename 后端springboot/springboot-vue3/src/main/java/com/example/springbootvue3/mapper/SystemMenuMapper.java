package com.example.springbootvue3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootvue3.entity.SystemMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统菜单表 Mapper 接口
 * </p>
 *
 * @author 丁祥
 * @since 2022-10-06
 */
@Mapper
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

}
