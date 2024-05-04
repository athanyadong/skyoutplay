package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @ApiOperation("修改商品")
    @PutMapping
    public Result  updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return Result.success();
    }


    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealDTO){
        log.info("员工分页查询。参数为：{}",setmealDTO);
        PageResult result = setmealService.pageQuery(setmealDTO);
        return Result.success(result);
    }

    /**
     * 根据id查询套餐，用于修改页面回显数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }


    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result delete(@RequestParam List<Long> ids){
        log.info("删除套餐。参数为：{}",ids);
        setmealService.deleteByIds(ids);


        return Result.success();
    }
    /**
     * 套餐起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}


