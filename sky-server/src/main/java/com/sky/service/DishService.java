package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
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

    /**
     * 根据id查询对应的菜品和口味数据
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);


    /**
     * 根据id修改菜品基本信息和对应的口味信息
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    List<Dish> list(Long categoryId);
}
