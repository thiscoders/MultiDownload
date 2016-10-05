package com.lgiue.download;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载器
 * Created by ye on 10/5/16.
 */
public class Downloader {
    //下载资源的url
    private String urls;
    //缓存文件保存路径
    private String tempPath;
    //文件下载位置
    private String downPath;
    //下载需开启的线程数
    private int threadCount;
    //当前为下载正在运行的线程数
    private int runningThread;

    /**
     *
     * @param urls  资源的URL
     * @param tempPath 缓存路径
     * @param downPath 下载路径
     * @param threadCount 下载开启的线程数
     */
    public Downloader(String urls,String tempPath,String downPath,int threadCount){
        //为各项属性赋值
        this.urls=urls;
        this.tempPath=tempPath;
        this.downPath=downPath;
        this.threadCount=threadCount;
        //使当前运行线程等于总线程数
        this.runningThread=threadCount;
    }

    /**
     * 下载
     */
    public void download(){
        try {
            URL url=new URL(this.urls);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            int resCode=connection.getResponseCode();
            if(resCode!=200){
                System.out.println("资源请求失败，响应码："+resCode);
                return;
            }
            int len=connection.getContentLength();
            System.out.println("文件长度如下："+len);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
