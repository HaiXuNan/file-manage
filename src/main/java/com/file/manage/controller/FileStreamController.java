package com.file.manage.controller;

import com.file.manage.entity.ResponseEntity;
import com.file.manage.service.FileStreamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "文件流操作")
@Controller
@RequestMapping("/stream")
public class FileStreamController {

    private final FileStreamService fileStreamService;

    public FileStreamController(FileStreamService fileStreamService, HttpServletResponse response) {
        this.fileStreamService = fileStreamService;
        this.response = response;
    }

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> uploadFile(MultipartFile multipartFile) {

        return fileStreamService.uploadFile(multipartFile);
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/delete/{filePath}")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable String filePath) {
        return fileStreamService.deleteFile(filePath);
    }

    private final HttpServletResponse response;

    @ApiOperation("下载文件")
    @PutMapping("/download")
    public void downloadFile(String filePath) {
        fileStreamService.downloadFile(filePath, response);
    }

    @ApiOperation("下载文件到指定目录")
    @PostMapping("/downloadLocal")
    @ResponseBody
    public ResponseEntity<String> downloadLocal(String filePath, String localPath) {
        return fileStreamService.downloadLocal(filePath, localPath);
    }

    @ApiOperation("测试")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String test(@PathVariable String id) {
        System.out.println("id:" + id);
        return id;
    }

}
