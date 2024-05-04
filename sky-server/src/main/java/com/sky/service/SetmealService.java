package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {


    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealDTO);

    void saveWithDish(SetmealDTO setmealDTO);

    void deleteByIds(List<Long> ids);

    SetmealVO getByIdWithDish(Long id);

    void startOrStop(Integer status, Long id);
}
