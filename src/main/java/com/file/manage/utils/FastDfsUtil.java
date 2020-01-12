package com.file.manage.utils;


import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.*;

@Slf4j
public class FastDfsUtil {

    private static StorageServer storageServer = null;
    private static StorageClient storageClient = null;

    static {
        try {
            String confFileName = "conf/fdfs_client.conf";
            ClientGlobal.init(confFileName);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            storageClient = new StorageClient(trackerServer, storageServer);

        } catch (IOException | MyException e) {
            log.error("error", e);
        }
    }

    /**
     * @param @param  inputStream 文件流
     * @param @param  filename 文件名称
     * @param @return
     * @param @throws IOException
     * @param @throws MyException
     * @return String 返回文件在FastDfs的存储路径
     * @throws
     * @Title: fdfsUpload
     * @Description: 通过文件流上传文件
     */
    public static String fdfsUpload(InputStream inputStream, String filename) throws IOException, MyException {
        String suffix = ""; //后缀名
        try {
            suffix = filename.substring(filename.lastIndexOf(".") + 1);
        } catch (Exception e) {
            throw new RuntimeException("参数filename不正确!格式例如：a.png");
        }
        StringBuilder savepath = new StringBuilder(); //FastDfs的存储路径
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buff)) != -1) {
            swapStream.write(buff, 0, len);
        }
        byte[] in2b = swapStream.toByteArray();
        String[] strings = storageClient.upload_file(in2b, suffix, null); //上传文件
        for (String str : strings) {
            savepath.append("/").append(str); //拼接路径
        }
        return savepath.toString();
    }

    /**
     * @param @param  filepath 本地文件路径
     * @param @return
     * @param @throws IOException
     * @param @throws MyException
     * @return String 返回文件在FastDfs的存储路径
     * @throws
     * @Title: fdfsUpload
     * @Description: 本地文件上传
     */
    public static String fdfsUpload(String filepath) throws IOException, MyException {
        String suffix = ""; //后缀名
        try {
            suffix = filepath.substring(filepath.lastIndexOf(".") + 1);
        } catch (Exception e) {
            throw new RuntimeException("上传的不是文件!");
        }
        StringBuilder savepath = new StringBuilder(); //FastDfs的存储路径
        //String[] strings = storageClient.upload_file(filepath.getBytes(), suffix, null);
        String[] strings = storageClient.upload_file(filepath, suffix, null); //上传文件
        for (String str : strings) {
            savepath.append("/").append(str); //拼接路径
        }
        return savepath.toString();
    }

    /**
     * @param @param  savepath 文件存储路径
     * @param @param  localPath 下载目录
     * @param @return
     * @param @throws IOException
     * @param @throws MyException
     * @return boolean 返回是否下载成功
     * @throws
     * @Title: fdfsDownload
     * @Description: 下载文件到目录
     */
    public static boolean fdfsDownload(String savepath, String localPath) throws IOException, MyException {
        String group = ""; //存储组
        String path = ""; //存储路径
        try {
            int secondindex = savepath.indexOf("/", 2); //第二个"/"索引位置
            group = savepath.substring(1, secondindex); //类似：group1
            path = savepath.substring(secondindex + 1); //类似：M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
        int result = storageClient.download_file(group, path, localPath);
        if (result != 0) {
            throw new RuntimeException("下载文件失败：文件路径不对或者文件已删除!");
        }
        return true;
    }

    /**
     * @param @param  savepath 文件存储路径
     * @param @return
     * @param @throws IOException
     * @param @throws MyException
     * @return byte[] 字符数组
     * @throws
     * @Title: fdfsDownload
     * @Description: 返回文件字符数组
     */
    public static byte[] fdfsDownload(String savepath) throws IOException, MyException {
        byte[] bs = null;
        String group = ""; //存储组
        String path = ""; //存储路径
        try {
            int secondindex = savepath.indexOf("/", 2); //第二个"/"索引位置
            group = savepath.substring(1, secondindex); //类似：group1
            path = savepath.substring(secondindex + 1); //类似：M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
        bs = storageClient.download_file(group, path); //返回byte数组
        return bs;
    }

    /**
     * @param @param  savepath 文件存储路径
     * @param @return
     * @param @throws IOException
     * @param @throws MyException
     * @return boolean 返回true表示删除成功
     * @throws
     * @Title: fdfsDeleteFile
     * @Description: 删除文件
     */
    public static boolean fdfsDeleteFile(String savepath) throws IOException, MyException {
        String group = ""; //存储组
        String path = ""; //存储路径
        try {
            int secondindex = savepath.indexOf("/", 2); //第二个"/"索引位置
            group = savepath.substring(1, secondindex); //类似：group1
            path = savepath.substring(secondindex + 1); //类似：M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
        int result = storageClient.delete_file(group, path); //删除文件，0表示删除成功
        if (result != 0) {
            throw new RuntimeException("删除文件失败：文件路径不对或者文件已删除!");
        }
        return true;
    }

    /**
     * @param @param  savepath 文件存储路径
     * @param @return
     * @param @throws IOException
     * @param @throws MyException
     * @return FileInfo 文件信息
     * @throws
     * @Title: fdfdFileInfo
     * @Description: 返回文件信息
     */
    public static FileInfo fdfdFileInfo(String savepath) throws IOException, MyException {
        String group = ""; //存储组
        String path = ""; //存储路径
        try {
            int secondindex = savepath.indexOf("/", 2); //第二个"/"索引位置
            group = savepath.substring(1, secondindex); //类似：group1
            path = savepath.substring(secondindex + 1); //类似：M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png
        } catch (Exception e) {
            throw new RuntimeException("传入文件存储路径不正确!格式例如：/group1/M00/00/00/wKgBaFv9Ad-Abep_AAUtbU7xcws013.png");
        }
        return storageClient.get_file_info(group, path);
    }


    public static void main(String[] args) {
        FastDfsUtil fast = new FastDfsUtil();
        try {
            String str = "C:\\Users\\xhn\\Desktop\\test.txt";
            FileInputStream inputStream = new FileInputStream(new File(str));
//            InputStream stream = new ByteArrayInputStream(str.getBytes());
            String txt = fast.fdfsUpload(inputStream, "txt");
            System.out.println(txt);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }
}

