package com.example.javalib.reflectDemo;

/**
 * @author yys
 * @since 2022/08/17
 */
public class Student {
    public Student() {
        System.out.println("创建了一个Student实例");
    }

    private String name;

    public void setName1() {
        System.out.println("调用了无参方法setName1()");
    }

    public void setName2(String str) {
        System.out.println("调用了有参方法setName2(String str)" + str);
    }
}
