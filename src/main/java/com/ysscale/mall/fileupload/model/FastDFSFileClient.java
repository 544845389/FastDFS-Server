package com.ysscale.mall.fileupload.model;


import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 * @author 侯存路
 * @date 2019/3/21
 * @company codingApi
 * @description
 */
@Slf4j
public class FastDFSFileClient {


    private  static TrackerClient trackerClient;


    static {
        try {
            System.out.println("配置文件地址-->");
            InputStream  filePath = ClassUtils.class.getClassLoader().getResourceAsStream("fdfs_client.conf");
            Properties props = new Properties();
            props.load(filePath);
            System.out.println("配置文件地址-->"+filePath);
            ClientGlobal.initByProperties(props);

            trackerClient = new TrackerClient();
        } catch (Exception e) {
            log.error("fdfs 初始化失败！");
            e.printStackTrace();
        }
    }





     private   static  StorageClient  getStorageClient(){
         TrackerServer trackerServer;
         StorageServer storageServer;
         StorageClient storageClient = null;
         try {
             trackerServer = trackerClient.getConnection();
             storageServer = trackerClient.getStoreStorage(trackerServer);
             storageClient = new StorageClient(trackerServer, storageServer);
         } catch (IOException e) {
             e.printStackTrace();
         }
         return  storageClient;
     }




    public static String upload(FastDFSFile file) {
        StorageClient storageClient =   getStorageClient();
        log.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("name", file.getName());
        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        try {
            uploadResults = getStorageClient().upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (Exception e) {
            log.info("upload file fail! " , e.getMessage());
            e.printStackTrace();
        }
        log.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");
        if (uploadResults == null) {
            log.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        log.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
        return groupName+"/"+remoteFileName;
    }






    public static DownFile downFile(String groupName, String remoteFileName) {
        DownFile downFile = new DownFile();
        StorageClient storageClient =  getStorageClient();
        try {
            String fileName  = storageClient.get_metadata(groupName , remoteFileName)[0].getValue();
            downFile.setFileName(fileName);

            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileByte);
            downFile.setInputStream(ins);
            return downFile;
        } catch (Exception e) {
            log.error("下载文件失败！"  , e.getMessage());
            e.printStackTrace();
        }
        return downFile;
    }









}
