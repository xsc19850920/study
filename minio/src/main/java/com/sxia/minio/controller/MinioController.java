package com.sxia.minio.controller;

import com.sxia.minio.common.MinioUtil;
import com.sxia.minio.config.MinioProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioProperties minioProperties;

    @PostMapping(value = "/upload")
    public String upload(@RequestParam(name = "file") MultipartFile multipartFile) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
        MinioUtil.createBucket(minioProperties.getBucket());
        MinioUtil.uploadFile(minioProperties.getBucket(), multipartFile, fileName);
        return MinioUtil.getPreSignedObjectUrl(minioProperties.getBucket(), fileName);
    }
}
