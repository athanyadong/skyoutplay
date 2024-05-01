package com.sky.service;

import com.sky.dto.DishDTO;
import org.springframework.stereotype.Service;

public interface DishService {

    /**
     * 添加菜品和口味数据
     * @param dishDTO
     */
    void saveWith(DishDTO dishDTO);
}
