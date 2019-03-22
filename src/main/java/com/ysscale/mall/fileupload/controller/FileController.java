package com.ysscale.mall.fileupload.controller;

import com.ysscale.mall.fileupload.service.FastDFSFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @author 侯存路
 * @date 2019/3/21
 * @company codingApi
 * @description
 */
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FastDFSFileService  fastDFSFileService;



    @PostMapping("/fileUpload")
    public  String  fileUpload( @RequestPart("file") MultipartFile  file ){
          return  fastDFSFileService.fileUpload(file);
    }



    @GetMapping("/downFile")
    public ResponseEntity<InputStreamResource> downFile(@RequestParam("url") String url , HttpServletResponse response  ){
         return fastDFSFileService.downFile(url , response);
    }




}
