package com.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileTest {
    public static void main(String[] args) throws IOException {
        //创建一个file对象，指向一个文件，打印出文件的大小
        File file = new File("C:\\Users\\86177\\Documents\\TEST.txt");
        //地址也可以是C:/Users/86177/Documents/TEST.txt 分隔符也可以是File.separator
        System.out.println(file.length());
        //File也可以指向文件夹，但是只能获取文件夹本身的大小，无法获取里面的内容大小
        //File也可以指代不存在的文件对象
        File file3 = new File("C:\\Users\\86177\\Documents\\TEST1.txt");
        System.out.println(file3.length());
        System.out.println(file3.exists());
        //绝对路径和相对路劲，指向模块中的文件时，使用相对路径
        //path直接使用工程下的相对路径
        File newFile = new File("C:\\Users\\86177\\Documents\\TEST2.txt");
        System.out.println(newFile.createNewFile());
        File file1 = new File("C:\\Users\\86177\\Documents");
        String[] names =  file1.list();
        System.out.println(Arrays.stream(names).toList());
        File[] files = file1.listFiles();
        for (File filer:files) {
            System.out.println(filer.getAbsoluteFile());
        }
    }

}
