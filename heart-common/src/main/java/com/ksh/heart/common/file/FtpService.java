package com.ksh.heart.common.file;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * ftp工具
 */
public abstract class FtpService {
    /**
     * ftp配置信息
     */
    FtpConfig config;

    /**
     * 上传文件附件到ftp服务器
     */
    public abstract Map<String, String> ftpUpload(MultipartFile file, String fileName, String fileType);

    /**
     * 功能：根据文件名称，下载文件流
     */
    public abstract void ftpDownload(String path, String ftpFileName, String fileName, HttpServletResponse response);

    /**
     * 删除文件
     * @param path     FTP服务器保存目录
     * @param fileName 要删除的文件名称
     */
    public abstract boolean ftpDelete(String path, String fileName);
}
