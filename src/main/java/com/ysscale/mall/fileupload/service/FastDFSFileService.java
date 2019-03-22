package com.ysscale.mall.fileupload.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 侯存路
 * @date 2019/3/21
 * @company codingApi
 * @description
 */
public interface FastDFSFileService {


    /**
     * 上传文件
     * @param file
     * @return
     */
    String fileUpload(MultipartFile file);

    /**
     * 下载文件
     * @param url
     * @param response
     * @return
     */
    ResponseEntity<InputStreamResource> downFile(String url, HttpServletResponse response);
}
