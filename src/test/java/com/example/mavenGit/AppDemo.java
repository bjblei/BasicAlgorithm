package com.example.mavenGit;

/**
 * Created by leibao on 2018/6/22.
 */
public class AppDemo {

    public static void main(String[] args) {
        System.out.print("clz1=" + Integer.class.getClasses());
        System.out.print("clz2=" + (new Integer(3).getClass()));

    }
}
