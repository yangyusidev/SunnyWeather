package com.example.javalib.reflectDemo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yys
 * @since 2022/08/17
 */
public class Reflect {
    public static void main(String[] args) {
        Class<Student> studentClass = Student.class;

        Object mStudent = null;

        try {
            mStudent = studentClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3.2 通过Class对象获取方法setName2（）的Method对象:需传入方法名 & 参数类型
        Method mSetName2 = null;
        try {
            mSetName2 = studentClass.getMethod("setName2", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            // 通过Method对象调用setName2（）：需传入创建的实例 & 参数值
            if (mSetName2 != null) {
                mSetName2.invoke(mStudent, "Carson_Ho");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            // 3. 通过Class对象获取Student类的name属性
            Field f = studentClass.getDeclaredField("name");
            // 4. 设置私有访问权限
            f.setAccessible(true);
            // 5. 对新创建的Student对象设置name值
            f.set(mStudent, "Carson_Ho");
            // 6. 获取新创建Student对象的的name属性 & 输出
            System.out.println(f.get(mStudent));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
