package com.example.xiaopeng.data.data;

import android.content.Context;

//import com.example.xiaopeng.chatwindow.MsgAdapter;

import org.litepal.crud.LitePalSupport;
//寄存器
public class Register extends LitePalSupport {

    private int id;
    public  int getId()       { return id; }
    public void setId(int id) { this.id = id; }


    //名字
    private String name;
    public  String getName()            { return name; }
    public  void   setName(String name) { this.name = name; }

    //元素
    private String element;
    public  String getElement()            { return element; }
    public  void   setElement(String element) { this.element = element; }


}
