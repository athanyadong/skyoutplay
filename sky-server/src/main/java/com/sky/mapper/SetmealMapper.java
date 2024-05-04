package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annoutation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select count(id) from setmeal where id=#{id}")
    Integer countByCategoryId(Long id);

    Page<SetmealVO> pageSelete(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    @Select("select * from setmeal where id = #{id}")
    Setmeal seleteById(Long id);

    void deleteByIds(@Param("list")List<Long> ids);

    @Select(
            "select * from setmeal where id = #{id}"
    )
    Setmeal getById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
}
