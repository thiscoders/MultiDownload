package com.lgiue.utils;

/**
 * 下载器的工具类
 * Created by ye on 10/5/16.
 */
public class DUtils {

    /**
     * 获取下载文件的路径以及文件名称
     * @param path 路径（缓存或者下载路径）
     * @param urls url地址
     * @return 完整的本地保存路径
     */
    public static String getFileTitleAndPath(String path,String urls){
        int pLen=path.length();
        int pIndex=path.lastIndexOf("/");
        if(pIndex<pLen-1){
            path+="/";
        }

        int uIndex=urls.lastIndexOf("/");
        return path+new String(urls.substring(uIndex+1));
    }


}
