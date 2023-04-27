package com.geostar.geoonline.entityserverhtml.servicepublish.entity;

import io.minio.MinioClient;

/**
 * @Description: MinioConfig
 * @Date: 2023/4/3 13:50
 * @Author: Ten Locks
 */
public class MinioConfig {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public MinioClient minioClient() {
        return MinioClient.builder().credentials(accessKey, secretKey).endpoint(endpoint).build();
    }
}
