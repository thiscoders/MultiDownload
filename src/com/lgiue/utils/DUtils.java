package com.lgiue.utils;

/**
 * 下载器的工具类
 * Created by ye on 10/5/16.
 */
public class DUtils {

    /**
     * 根据url获取下载的文件名
     * @param urls url路径
     * @return  文件名
     */
    public static String getFileTitle(String urls){
        int index=urls.lastIndexOf("/");
        return new String(urls.substring(index+1));
    }


}
