package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import io.swagger.models.auth.In;

import java.util.List;

public interface CateGoryService {
    /**
     * 修改分类
     * @param categoryDTO
     */
    void updateCate(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQueryCate(CategoryPageQueryDTO categoryPageQueryDTO);

    void startOrStop(Integer status, Long id);


    void insertCate(CategoryDTO categoryDTO);

    void deleteCateById(Long id);

    List<Category> selectByType(Integer type);
}
