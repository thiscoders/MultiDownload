package com.lgiue.download;

import java.net.URL;

/**
 * 下载器
 * Created by ye on 10/5/16.
 */
public class Downloader {
    //下载资源的url
    private URL url;
    //缓存文件保存路径
    private String tempPath;
    //文件下载位置
    private String downPath;
    //下载需开启的线程数
    private int threadCount;
    //当前为下载正在运行的线程数
    private int runningThread;



    public Downloader(URL url,String tempPath,String downPath,int threadCount){
        //为各项属性赋值
        this.url=url;
        this.tempPath=tempPath;
        this.downPath=downPath;
        this.threadCount=threadCount;
        //使当前运行线程等于总线程数
        this.runningThread=threadCount;
    }

}
