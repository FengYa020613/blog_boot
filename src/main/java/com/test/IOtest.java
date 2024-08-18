package com.test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class IOtest {
    public static void main(String[] args) throws IOException {

        /*//创建对象
        InputStreamReader isr = new InputStreamReader(new FileInputStream("test.txt"), StandardCharsets.UTF_8);
        FileReader fr = new FileReader("test.txt",StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        int ch;
        while ((ch = br.read()) != -1) {
            System.out.println((char)ch);
        }
        int len;
        char[] ch1 = new char[2];
        while ((len = fr.read(ch1)) != -1) {
            System.out.println(new String(ch1,0,len));
        }
        //读取数据
        //字符流底层也是字节流，当遇到中文时，读取多个字节
        //读取数据最后返回为十进制

        while ((ch = fr.read()) != -1) {
            System.out.println((char)ch);
        }
        //释放资源
        fr.close();*/
        FileWriter fw = new FileWriter("test.txt",StandardCharsets.UTF_8);
        fw.write("我");
        fw.close();
    }
}
