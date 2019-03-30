package com.example.xiaopeng.data.data;

import org.litepal.crud.LitePalSupport;
//答案找问题
public class DataProblem extends LitePalSupport {


    private int id;
    public  int getId()       { return id; }
    public void setId(int id) { this.id = id; }

    //主题
    private String theme;
    public  String getTheme()            { return theme; }
    public  void   setTheme(String theme) { this.theme = theme; }



    //下一个句子
    private String next;
    public  String getNext()            { return next; }
    public  void   setNext(String next) { this.next = next; }

    //当前句子
    private String problem;
    public  String getProblem()            { return problem; }
    public  void   setProblem(String problem) { this.problem = problem; }

    //问题
    private String problemenglish;
    public String getProblemenglish()               { return problemenglish; }
    public  void  setProblemenglish(String problemenglish) { this.problemenglish = problemenglish; }

    //问题
    private String problemchinese;
    public String getProblemchinese()               { return problemchinese; }
    public  void  setProblemchinese(String problemchinese) { this.problemchinese = problemchinese; }


    //答案
    private int answerenglishnumber;
    public  int getAnswerenglishnumber()              { return answerenglishnumber; }
    public   void  setAnswerenglishnumber(int answerenglishnumber) { this.answerenglishnumber = answerenglishnumber; }


    //答案
    private int answerchinesenumber;
    public  int getAnswerchinesenumber()              { return answerchinesenumber; }
    public   void  setAnswerchinesenumber(int answerchinesenumber) { this.answerchinesenumber = answerchinesenumber; }
}
