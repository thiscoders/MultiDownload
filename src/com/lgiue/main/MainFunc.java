package com.lgiue.main;

import com.lgiue.download.Downloader;

/**
 *
 * Created by ye on 10/4/16.
 */
public class MainFunc {
    private static String urls="http://127.0.0.1:8080/share/windowsdo/apink.mp4";
    private static String tempPath="/home/ye/Downloads/kaifa/temp/";
    private static String downPath="/home/ye/Downloads/kaifa/";
    private static int threadCount=5;

    public static void main(String[] args){
        Downloader downloader=new Downloader(urls,tempPath,downPath,threadCount);
        downloader.download();
    }

}
