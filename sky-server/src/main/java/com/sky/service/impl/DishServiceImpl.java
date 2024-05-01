package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 多个数据表操作要添加事物，保证数据的一致性
     *
     * @param dishDTO
     */
    @Override
    @Transactional//打开事务
    public void saveWith(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        Integer i = dishMapper.selectByDishName(dishDTO.getName());
        if (i > 0) {
            throw new RuntimeException("菜品已存在");
        }
        //菜品表添加数据
        dishMapper.save(dish);
        //在mapper.xml中编写，添加成功后会吧id的值返回，故而能接收到
        Long id = dish.getId();

        //口味表添加n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                        dishFlavor.setDishId(id);
                    }
            );
            dishFlavorMapper.insertBatch(flavors);
        }

    }
}
