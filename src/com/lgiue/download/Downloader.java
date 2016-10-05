package com.lgiue.download;

import com.lgiue.utils.DUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 下载器的类
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

    private String destFilePath;
    /**
     *
     * @param urls  下载资源的url
     * @param tempPath 缓存文件保存路径
     * @param downPath 文件下载位置
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
        this.destFilePath=DUtils.getFileTitleAndPath(this.downPath,this.urls);
    }

    /**
     * 下载方法
     */
    public void download(){
        try {
            //组拼connection获取资源长度
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

            File dir=new File(this.downPath);
            if(!dir.exists()){
                boolean res=dir.mkdirs();
                if(!res){
                    System.out.println("下载目录创建失败！");
                }
            }

            //在本地创建下载文件的镜像
            RandomAccessFile file=new RandomAccessFile(this.destFilePath,"rw");
            file.setLength(len);

            //计算每一个线程的下载大小，以及开始下载位置，结束位置
            int blockSize=len/this.threadCount;
            int start;
            int end;
            for(int i=0;i<this.threadCount;i++){
                start=i*blockSize;
                end=(i+1)*blockSize-1;
                if(i==this.threadCount-1){
                    end=len;
                }
                //开启下载线程
                new downThread(i, start, end).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class downThread extends Thread{
        private int threadID;
        private int istart;
        private int iend;
        private String destTempPath;

        private downThread(int threadID,int istart,int iend){
            this.threadID=threadID;
            this.istart=istart;
            this.iend=iend;
            this.destTempPath=DUtils.getFileTitleAndPath(Downloader.this.tempPath,threadID+".txt");
        }

        @Override
        public void run() {
            try {
                URL url=new URL(Downloader.this.urls);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                File item=new File(this.destTempPath);
                if(item.exists()&&item.length()!=0){
                    BufferedReader reader=new BufferedReader(new FileReader(item));
                    String last=reader.readLine();
                    if(!last.isEmpty()){
                        this.istart=Integer.parseInt(last);
                    }
                    reader.close();
                }


                connection.addRequestProperty("Range","bytes="+this.istart+"-"+this.iend);

                int resCode=connection.getResponseCode();
                if(resCode!=206){
                    System.out.println("分段请求资源失败，响应码："+resCode);
                    return;
                }


                //获取inputstream
                InputStream stream=connection.getInputStream();
                RandomAccessFile part=new RandomAccessFile(Downloader.this.destFilePath,"rw");
                part.seek(istart);

                File tdir=new File(Downloader.this.tempPath);
                if(!tdir.exists()){
                    boolean res=tdir.mkdirs();
                    if(!res){
                        System.out.println("缓存目录创建失败！");
                    }
                }
                int totle=0;

                int len;
                byte[] buffer=new byte[1024*1024];
                while ((len=stream.read(buffer))!=-1){
                    part.write(buffer,0,len);
                    totle+=len;

                    RandomAccessFile temp=new RandomAccessFile(this.destTempPath,"rwd");
                    int last=this.istart+totle;
                    temp.write(String.valueOf(last).getBytes());
                    temp.close();
                }
                System.out.println("线程"+this.threadID+"下载完成！");
                synchronized (this){
                    Downloader.this.runningThread--;
                    if(Downloader.this.runningThread==0){
                        for(int i=0;i<Downloader.this.threadCount;i++){
                            String tpath=DUtils.getFileTitleAndPath(Downloader.this.tempPath,i+".txt");
                            File tfile=new File(tpath);
                            if(tfile.exists()){
                                //删除缓存文件
                                //tfile.delete();
                                System.out.println("去掉注释，删除文件！");
                            }
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
