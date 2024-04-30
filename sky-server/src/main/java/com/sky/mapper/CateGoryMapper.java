package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annoutation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CateGoryMapper {
    /**
     * 修改分类
     * @param build
     */
    @AutoFill(value = OperationType.UPDATE)
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
    @AutoFill(value = OperationType.INSERT)

    void insertCate(Category build);

    void deleteCateById(Long id) ;


    List<Category> selectByType(Integer type);
}
