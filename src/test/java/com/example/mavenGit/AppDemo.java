package com.example.mavenGit;

/**
 * Created by leibao on 2018/6/22.
 */
public class AppDemo {

    public static void main(String[] args) {
//        System.out.print("clz1=" + Integer.class.getClasses());
//        System.out.print("clz2=" + (new Integer(3).getClass()));

        int a=2;
        char c = '1';
        char ch = (char)(c +a);
        char ch_a = (char) (1+a);
        System.out.println("ch="+ch);
        System.out.println("ch_a="+(char)ch_a);



    }

}
