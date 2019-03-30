package com.example.xiaopeng.data.data;

import org.litepal.crud.LitePalSupport;

public class DailyEnglish extends LitePalSupport {

    private int id;
    public  int getId()       { return id; }
    public void setId(int id) { this.id = id; }


    //名字
    private String nameenglish;
    public  String getNameenglish()            { return nameenglish; }
    public  void   setNameenglish(String nameenglish) { this.nameenglish = nameenglish; }

    //名字
    private String namechinese;
    public  String getNamechinese()            { return namechinese; }
    public  void   setNamechinese(String namechinese) { this.namechinese = namechinese; }

    //元素
    private String elementenglish;
    public  String getElementenglish()            { return elementenglish; }
    public  void   setElementenglish(String elementenglish) { this.elementenglish = elementenglish; }

    //元素
    private String elementchinese;
    public  String getElementchinese()            { return elementchinese; }
    public  void   setElementchinese(String elementchinese) { this.elementchinese = elementchinese; }

    private int imageId;
    public  int getImageId()            { return imageId; }
    public void setImageId(int imageId) { this.imageId = imageId; }




}