package com.ysscale.mall.fileupload.service.impl;

import com.ysscale.mall.fileupload.model.DownFile;
import com.ysscale.mall.fileupload.model.FastDFSFile;
import com.ysscale.mall.fileupload.model.FastDFSFileClient;
import com.ysscale.mall.fileupload.service.FastDFSFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 侯存路
 * @date 2019/3/21
 * @company codingApi
 * @description
 */
@Service
@Slf4j
public class FastDFSFileServiceImpl implements FastDFSFileService {





    @Override
    public String fileUpload(MultipartFile file) {
        byte[] content = null;
        try {
            content =    file.getBytes();
        } catch (IOException e) {
            log.error("获取文件错误！" ,e.getMessage());
            e.printStackTrace();
        }
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1 , file.getOriginalFilename().length());
        FastDFSFile fastDFSFile = new FastDFSFile();
        fastDFSFile.setContent(content);
        fastDFSFile.setName(file.getOriginalFilename());
        fastDFSFile.setExt(ext);
        String  url =   FastDFSFileClient.upload(fastDFSFile);
        return url;
    }





    @Override
    public ResponseEntity<InputStreamResource>  downFile(String url, HttpServletResponse response) {
        String group =  url.substring(  0 , url.indexOf("/"));
        String remoteFileName =  url.substring(  url.indexOf("/") + 1 , url.length() );
        DownFile downFile =  FastDFSFileClient.downFile(group , remoteFileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", downFile.getFileName()));
        headers.add("Pragma", "no-cache");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(downFile.getInputStream()));
    }




}
