package com.file.manage.service;

import com.file.manage.entity.ResponseEntity;
import com.file.manage.entity.StatusCode;
import com.file.manage.exception.ServerException;
import com.file.manage.utils.DateUtil;
import com.file.manage.utils.FileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

@Slf4j
@Service
public class FileStreamService {

    @Value("${system.file.root.path}")
    private String filePath;

    private static String dateStr = DateUtil.getFormatStr(DateUtil.yMd);

    public ResponseEntity<String> uploadFile(MultipartFile multipartFile) {

        String originalFilename = multipartFile.getOriginalFilename();
        Optional.ofNullable(originalFilename).orElseThrow(() -> new ServerException(StatusCode.FAILURE));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String completePath = filePath + dateStr + suffix;
        File file = new File(completePath);
        try {
            FileUtils.forceDelete(file);
            multipartFile.transferTo(file);

            operateFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("文件上传失败：{}", e.getMessage());
            throw new ServerException(StatusCode.UPLOAD_FAIL);
        }
        if (!file.exists()) {
            return ResponseEntity.failure(StatusCode.UPLOAD_FAIL);
        }
        return ResponseEntity.success(completePath);
    }

    /**
     * 处理文件
     */
    private void operateFile(File file) throws IOException {
        String name = file.getName();
        String suffix = name.substring(name.indexOf(".") + 1);

        if (FileType.text.contains(suffix)) {
            String dest = filePath + dateStr + "/" + suffix + "/" + name;
            File destFile = new File(dest);
            FileUtils.forceDelete(destFile);
            dealFile(file, destFile);
            if (!file.exists()) {
                throw new ServerException(StatusCode.UPLOAD_FAIL);
            }
        }
        throw new ServerException(StatusCode.UPDATE_TYPE_FAIL);
    }

    /**
     * 处理视频
     */
    private void operateVideo() {

    }

    /**
     * 处理音乐
     */
    private void operateMusic() {

    }

    private void dealFile(File inFile, File destFile) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileInputStream inputStream = new FileInputStream(inFile);
            FileOutputStream outputStream = new FileOutputStream(destFile);
            while (inputStream.read(buffer.array()) != -1) {
                outputStream.write(buffer.array());
                buffer.flip();
            }
        } catch (IOException e) {
            log.debug("文件处理失败：{}", e.getMessage());
            throw new ServerException(StatusCode.UPLOAD_FAIL);
        }
    }
}
