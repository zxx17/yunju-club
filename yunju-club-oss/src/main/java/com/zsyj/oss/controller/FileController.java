package com.zsyj.oss.controller;

import com.zsyj.oss.entity.Result;
import com.zsyj.oss.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/3
 **/
@SuppressWarnings("unchecked")
@RestController
public class FileController {

    @Resource
    private FileService fileService;


    /**
     * 列出所有桶
     */
    @RequestMapping("/testGetAllBuckets")
    public String testGetAllBuckets() {
        List<String> allBucket = fileService.getAllBucket();
        return allBucket.get(0);
    }


    /**
     * 获取文件路径
     */
    @RequestMapping("/getUrl")
    public String getUrl(String bucketName, String objectName) {
        return fileService.getUrl(bucketName, objectName);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public Result<String> upload(MultipartFile uploadFile, String bucket, String objectName)  {
        String url = fileService.uploadFile(uploadFile, bucket, objectName);
        return Result.ok(url);
    }

}
