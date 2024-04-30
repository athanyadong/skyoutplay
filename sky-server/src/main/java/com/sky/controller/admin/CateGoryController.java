package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CateGoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CateGoryController {
    @Autowired
    private CateGoryService cateGoryService;

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @ApiOperation("修改分类")
    @PutMapping
    public Result updateCate(@RequestBody CategoryDTO categoryDTO){
        cateGoryService.updateCate(categoryDTO);
        return Result.success();
    }


    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQueryCate( CategoryPageQueryDTO categoryPageQueryDTO){
      PageResult result =cateGoryService.pageQueryCate(categoryPageQueryDTO);
        return Result.success(result);
    }


    @ApiOperation("菜单启用禁用分类")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        cateGoryService.startOrStop(status,id);

        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result insertCate(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}",categoryDTO);
        cateGoryService.insertCate(categoryDTO);

        return Result.success();
    }

    @ApiOperation("根据id删除分类")
    @DeleteMapping
    public Result deleteCateById(Long id){
        cateGoryService.deleteCateById(id);

        return Result.success();
    }
    @ApiOperation("根据类型查询分类")
    @GetMapping("/list")
    public Result selectByType(Integer type){
        List<Category> category=cateGoryService.selectByType(type);


        return Result.success(category);
    }

}
