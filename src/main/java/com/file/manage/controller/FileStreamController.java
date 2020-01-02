package com.file.manage.controller;

import com.file.manage.entity.ResponseEntity;
import com.file.manage.service.FileStreamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件流操作")
@RestController
@RequestMapping("/stream")
public class FileStreamController {

    private final FileStreamService fileStreamService;

    public FileStreamController(FileStreamService fileStreamService) {
        this.fileStreamService = fileStreamService;
    }

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile multipartFile) {

        return fileStreamService.uploadFile(multipartFile);
    }
}
