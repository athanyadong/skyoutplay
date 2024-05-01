package com.sky.mapper;

import com.sky.annoutation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {


    @Select("select count(id) from dish where category_id=#{id}")
    Integer countByCateGoryId(Long id);

    /**
     * 添加菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    /**
     * 根据名称查询，查看是否重复
     * @param name
     * @return
     */
    @Select("select count(name) from dish where name=#{name}")
    Integer selectByDishName(String name);

}
