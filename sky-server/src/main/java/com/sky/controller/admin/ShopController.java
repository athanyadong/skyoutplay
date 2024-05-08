package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {


    @Autowired
    private RedisTemplate redisTemplate;



    @PutMapping("/{status}")
    @ApiOperation("店铺状态设置")
    public Result setStatus(@PathVariable Long status){
        log.info("设置店铺状态位：{}",status==1?"营业中":"大洋了");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }

    @ApiOperation("获取店铺的营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");

        log.info("获取到的店铺状态位：{}",shopStatus==1?"营业中":"大洋了");

        return Result.success(shopStatus);
    }





}
