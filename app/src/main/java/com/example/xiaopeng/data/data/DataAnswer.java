package com.example.xiaopeng.data.data;

import org.litepal.crud.LitePalSupport;
//问题找答案
public class DataAnswer extends LitePalSupport {


    private int id;
    public  int getId()       { return id; }
    public void setId(int id) { this.id = id; }



    //答案
    private String answer;
    public  String getAnswer()            { return answer; }
    public  void   setAnswer(String answer) { this.answer = answer; }

    //主题
    private String theme;
    public  String getTheme()            { return theme; }
    public  void   setTheme(String theme) { this.theme = theme; }

    //问题
    private String problemenglish;
    public String getProblemenglish()               { return problemenglish; }
    public  void  setProblemenglish(String problemenglish) { this.problemenglish = problemenglish; }

    //问题
    private String problemchinese;
    public String getProblemchinese()               { return problemchinese; }
    public  void  setProblemchinese(String problemchinese) { this.problemchinese = problemchinese; }


}