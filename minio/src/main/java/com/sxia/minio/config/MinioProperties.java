package com.sxia.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author william@StarImmortal
 * @date 2022/9/4
 */
@Data
@Configuration
@ConfigurationProperties("minio")
public class MinioProperties {
    /**
     * 服务地址
     */
    private String endpoint;

    /**
     * 文件预览地址
     */
    private String preview;

    /**
     * 存储桶名称
     */
    private String bucket;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

}
