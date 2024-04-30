package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CateGoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CateGoryService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.beancontext.BeanContext;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CateGoryServiceImpl implements CateGoryService {

    @Autowired
    private CateGoryMapper cateGoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void updateCate(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //设置修改时间、修改人
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        cateGoryMapper.updateCate(category);
    }

    @Override
    public PageResult pageQueryCate(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = cateGoryMapper.selectPage(categoryPageQueryDTO);

        long total = page.getTotal();//获取总数
//        int pageSize = page.getPageSize();//获取显示数量
        List<Category> result = page.getResult();

        return new PageResult(total, result);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category build = Category.builder()
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .status(status)
                .build();
        cateGoryMapper.updateCate(build);

    }

    @Override
    public void insertCate(CategoryDTO categoryDTO) {
        Category build = Category.builder()
//                .createUser(BaseContext.getCurrentId())
//                .createTime(LocalDateTime.now())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .type(categoryDTO.getType())
                .id(categoryDTO.getId())
                .status(StatusConstant.DISABLE)
                .build();
        cateGoryMapper.insertCate(build);
    }

    @Override
    public void deleteCateById(Long id) {
        Integer count = dishMapper.countByCateGoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        Integer i = setmealMapper.countByCategoryId(id);
        if (i > 0) {
           throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        //删除当前分类
        cateGoryMapper.deleteCateById(id);
    }

    @Override
    public List<Category> selectByType(Integer type) {


        return cateGoryMapper.selectByType(type);
    }
}
