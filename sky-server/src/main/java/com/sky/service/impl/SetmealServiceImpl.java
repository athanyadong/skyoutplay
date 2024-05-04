package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {


    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;




    /**
     * 修改套餐
     *
     * @param setmealDTO
     * 1、修改套餐 2、删除关于套餐的菜品、 3、新的菜品重新绑定到这个套餐上
     */
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //1、修改套餐表，执行update
        setmealMapper.update(setmeal);

        //套餐id
        Long setmealId = setmealDTO.getId();

        //2、把关于这个套餐id的东西全部删除，
        // 删除套餐和菜品的关联关系，操作setmeal_dish表，执行delete
        setmealDishMapper.deleteBySetmealId(setmealId);
        //获取修改后的套餐菜品关联表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //把新的菜品绑定到这个套餐上
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        //3、重新插入套餐和菜品的关联关系，操作setmeal_dish表，执行insert
        setmealDishMapper.insertBatch(setmealDishes);
    }
    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //返回vo的
        SetmealVO setmealVO= new SetmealVO();
        //开始分页
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        //模糊查询，返回集合

        Page<SetmealVO> setmealVOS=setmealMapper.pageSelete(setmealPageQueryDTO);

//        for (SetmealVO set : setmealVOS) {
//            setmealDishMapper.seleteBySetmealId(set)
//        }

        return new PageResult(setmealVOS.getTotal(),setmealVOS);
    }

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {
        //创建套餐类
        Setmeal setmeal = new Setmeal();
        //dto塞进去
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //套餐信息塞进去
        setmealMapper.insert(setmeal);
        //获取生成的套餐id
        Long setmealId = setmeal.getId();
        //获取到套餐菜品关系表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //新增套餐会把菜品信息都带过来，只需要绑定一下套餐的id即可，
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        //全部写到套餐菜品关系表中即可
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 删除套餐
     * @param ids
     */
    @Transactional
    public void deleteByIds(List<Long> ids) {
        //查看删除的套餐中有没有在售卖的，在售卖的不能删除
        ids.forEach(id->{
            Setmeal setmeal=setmealMapper.seleteById(id);
            if (setmeal.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });

        setmealMapper.deleteByIds(ids);
        //删除套餐之前，要先删除对应的菜品关系
        setmealDishMapper.deleteByIds(ids);
    }

    /**
     * 根据id查询套餐和套餐菜品关系
     *
     * @param id
     * @return
     */
    public SetmealVO getByIdWithDish(Long id) {
        //根据套餐id查询套餐
        Setmeal setmeal = setmealMapper.getById(id);
        //根据套餐id查询套餐菜品关系表
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        //创建vo返回视图
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        //起售套餐时，判断套餐内是否有停售菜品，有停售菜品提示"套餐内包含未启售菜品，无法启售"
        if(status == StatusConstant.ENABLE){
            //select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = ?
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if(dishList != null && dishList.size() > 0){
                dishList.forEach(dish -> {
                    if(StatusConstant.DISABLE == dish.getStatus()){
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }

        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }
}
