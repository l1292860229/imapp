package com.coolwin;

/**
 * Created by dell on 2017/6/17.
 */
public class Test {
    public static void main(String[] args){
        String extName="123.doc".substring(0,"123.doc".lastIndexOf("."));
        System.out.println(extName);
    }
}
