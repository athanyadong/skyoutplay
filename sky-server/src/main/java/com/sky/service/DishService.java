package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {

    /**
     * 添加菜品和口味数据
     * @param dishDTO
     */
    void saveWith(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除功能
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
