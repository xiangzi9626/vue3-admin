package com.example.springbootvue3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootvue3.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 丁祥
 * @since 2022-10-04
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<Map<String, Object>> adminList(@Param("level") int level, @Param("limit1") int limit1, @Param("limit2") int limit2);

    List<User> userList(@Param("level") int level, @Param("limit1") int limit1, @Param("limit2") int limit2);
}
