package com.example.xiaopeng.data.data;

import org.litepal.crud.LitePalSupport;
//主题
public class DataTheme extends LitePalSupport {

    private int id;
    public  int getId()       { return id; }
    public void setId(int id) { this.id = id; }

    //分类
    private String type;
    public  String getType()            { return type; }
    public  void   setType(String type) { this.type = type; }

    //分类
    private String types;
    public  String getTypes()            { return types; }
    public  void   setTypes(String types) { this.types = types; }


    //主题
    private String theme;
    public  String getTheme()            { return theme; }
    public  void   setTheme(String theme) { this.theme = theme; }


    //名字
    private String nameenglish;
    public  String getNameenglish()            { return nameenglish; }
    public  void   setNameenglish(String nameenglish) { this.nameenglish = nameenglish; }

    //名字
    private String namechinese;
    public  String getNamechinese()            { return namechinese; }
    public  void   setNamechinese(String namechinese) { this.namechinese = namechinese; }

    //当前句子
    private String problem;
    public  String getProblem()            { return problem; }
    public  void   setProblem(String problem) { this.problem = problem; }

    private int imageId;
    public  int getImageId()            { return imageId; }
    public void setImageId(int imageId) { this.imageId = imageId; }


}