package com.file.manage.entity;

import lombok.Data;

@Data
public class RequestEntity {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String fileExtend;
    /**
     * 请求体
     */
    private String requestBody;

}
