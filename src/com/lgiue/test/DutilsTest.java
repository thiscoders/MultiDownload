package com.lgiue.test;

import com.lgiue.utils.DUtils;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ye on 10/5/16.
 */
public class DutilsTest {
    private String urls="http://127.0.0.1:8080/share/windowsdo/chrome.exe";
    private String downPath="/home/ye/Downloads/kaifa";
    @Test
    public void test01() throws MalformedURLException {
        URL url=new URL(urls);
        String urlt=url.toExternalForm();
        System.out.println(urlt);
    }

    @Test
    public void test02(){
        System.out.println( DUtils.getFileTitleAndPath(downPath,urls));
    }


}
