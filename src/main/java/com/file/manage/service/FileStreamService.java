package com.file.manage.service;

import com.file.manage.entity.ResponseEntity;
import com.file.manage.entity.StatusCode;
import com.file.manage.exception.ServerException;
import com.file.manage.utils.DateUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileStreamService {

    @Value("${system.file.root.path}")
    private String filePath;

    public ResponseEntity<String> uploadFile(MultipartFile multipartFile) {
        String dateStr = DateUtil.getFormatStr(DateUtil.yMd);

        String originalFilename = multipartFile.getOriginalFilename();
        Optional.ofNullable(originalFilename).orElseThrow(() -> new ServerException(StatusCode.FAILURE));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String completePath = filePath + dateStr + suffix;
        File file = new File(completePath);
        try {
            FileUtils.forceDelete(file);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException(StatusCode.UPLOAD_FAIL);
        }
        if (!file.exists()) {
            return ResponseEntity.failure(StatusCode.UPLOAD_FAIL);
        }
        return ResponseEntity.success(completePath);

    }
}
