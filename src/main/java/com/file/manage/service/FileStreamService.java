package com.file.manage.service;

import com.file.manage.entity.ResponseEntity;
import com.file.manage.entity.StatusCode;
import com.file.manage.exception.ServerException;
import com.file.manage.utils.DateUtil;
import com.file.manage.utils.FastDfsUtil;
import com.file.manage.utils.FileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
            if (file.exists())
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
            dealFile(file, name);
            if (!file.exists()) {
                throw new ServerException(StatusCode.UPLOAD_FAIL);
            }
        } else {
            throw new ServerException(StatusCode.UPDATE_TYPE_FAIL);
        }
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

    private void dealFile(File inFile, String fileName) {
        try (FileInputStream inputStream = new FileInputStream(inFile)) {
            FastDfsUtil.fdfsUpload(inputStream, fileName);
        } catch (IOException | MyException e) {
            log.debug("文件处理失败：{}", e.getMessage());
            throw new ServerException(StatusCode.UPLOAD_FAIL);
        }
    }

    /**
     * 删除文件信息
     *
     * @param filePath 文件在文件服务中的路径
     * @return 删除结果信息
     */
    public ResponseEntity<String> deleteFile(String filePath) {
        log.debug("删除文件为：{}", filePath);
        try {
            FastDfsUtil.fdfsDeleteFile(filePath);
        } catch (IOException | MyException e) {
            e.printStackTrace();
            log.debug("文件删除失败：{}", e.getMessage());
            throw new ServerException(StatusCode.DELETE_FILE_FAIL);
        }
        return ResponseEntity.success();
    }


    /**
     * 删除文件信息
     *
     * @param filePath 文件路径
     */
    public void downloadFile(String filePath, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            byte[] bytes = FastDfsUtil.fdfsDownload(filePath);
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException | MyException e) {
            e.printStackTrace();
            log.debug("文件下载失败：{}", e.getMessage());
            throw new ServerException(StatusCode.DOWNLOAD_FILE_FAIL);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载文件到指定目录
     *
     * @param filePath  文件路径
     * @param localPath 本地路径
     * @return 下载结果
     */
    public ResponseEntity<String> downloadLocal(String filePath, String localPath) {
        try {
            FastDfsUtil.fdfsDownload(filePath, localPath);
        } catch (IOException | MyException e) {
            e.printStackTrace();
            log.debug("文件下载到本地文件夹失败：{}", e.getMessage());
            throw new ServerException(StatusCode.DOWNLOAD_FILE_FAIL);
        }
        return ResponseEntity.success();
    }
}
