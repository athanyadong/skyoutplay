package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


/**
 * 同意借口
 */
@Api(tags = "通用接口")
@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;


    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("上传的文件名为：{}",file);
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀，dad.jpg
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName =UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String upload = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(upload);
        } catch (IOException e) {
          log.error("文件上传失败：{}",e);
        }

        return Result.error("文件上传失败");
    }
}
