package com.example.javalib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Admin
 */
public class MyClass {

    /**
     * 发生中断时记录位置的变量
     */
    private static int position = -1;

    public static void main(String[] args) {

        File sourceFile = new File("D:/", "text1.txt");
        File targetFile = new File("D:/", "text2.txt");

        FileInputStream fis = null;
        FileOutputStream fos = null;
        byte[] buf = new byte[1];
        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);

            while (fis.read(buf) != -1) {
                fos.write(buf);
                if (targetFile.length() == 3) {
                    position = 3;
                    throw new FileAccessException();
                }
            }
        } catch (FileAccessException e) {
            // 开启”续传“行为，即keepGoing方法.
            keepGoing(sourceFile, targetFile, position);
        } catch (FileNotFoundException e) {
            System.out.println("指定文件不存在");
        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
        } finally {
            try {
                // 关闭输入输出流
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void keepGoing(File sourceFile, File targetFile, int position) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            RandomAccessFile readFile = new RandomAccessFile(sourceFile, "rw");
            RandomAccessFile writeFile = new RandomAccessFile(targetFile, "rw");
            readFile.seek(position);
            writeFile.seek(position);
            byte[] buf = new byte[1];
            while (readFile.read(buf) != -1) {
                writeFile.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class FileAccessException extends Exception {

    }

}