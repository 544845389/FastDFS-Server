package com.ysscale.mall.fileupload.model;

import lombok.Data;

import java.io.InputStream;

/**
 * @author 侯存路
 * @date 2019/3/21
 * @company codingApi
 * @description
 */
@Data
public class DownFile {

    private  String  fileName;

    private InputStream inputStream;

}
