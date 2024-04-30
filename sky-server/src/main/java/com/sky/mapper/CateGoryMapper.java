package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CateGoryMapper {
    /**
     * 修改分类
     * @param build
     */
    void updateCate(Category build) ;

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> selectPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 添加
     * @param build
     */
    void insertCate(Category build);

    void deleteCateById(Long id) ;


    List<Category> selectByType(Integer type);
}
