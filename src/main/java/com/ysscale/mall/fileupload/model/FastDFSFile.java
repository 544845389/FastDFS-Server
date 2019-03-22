package com.ysscale.mall.fileupload.model;

import lombok.Data;

/**
 * @author 侯存路
 * @date 2019/3/21
 * @company codingApi
 * @description
 */
@Data
public class FastDFSFile {

    private String name;
    private byte[] content;
    private String ext;



}
