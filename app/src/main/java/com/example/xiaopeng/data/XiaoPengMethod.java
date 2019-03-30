package com.example.xiaopeng.data;

import com.example.xiaopeng.data.data.DailyEnglish;
//import com.example.xiaopeng.data.data.DataAnswer;
import com.example.xiaopeng.data.data.DataAnswer;
import com.example.xiaopeng.data.data.DataProblem;
import com.example.xiaopeng.data.data.DataTheme;
import com.example.xiaopeng.data.data.Register;
import com.example.xiaopeng.publicmethod.method;

//数据库方法
public class XiaoPengMethod extends method {


    //答案找问题
    protected void dataproblem(String[][] data) {

        for (int j = 1; j < data.length; j++) {
            for (int i = 2; i < data[j].length; i++) {
                DataProblem dataProblem = new DataProblem();
                dataProblem.setTheme(data[0][0]);
                dataProblem.setNext(data[j][1]);
                dataProblem.setProblem(data[j][0]);
                dataProblem.setProblemenglish(data[j][i++]);
                dataProblem.setAnswerenglishnumber(Integer.parseInt(data[j][i++]));
                dataProblem.setProblemchinese(data[j][i++]);
                dataProblem.setAnswerchinesenumber(Integer.parseInt(data[j][i]));
                dataProblem.save();
            }
        }
    }

    //问题找答案
    protected void dataanswer(String[][] data) {

        for (int j = 1; j < data.length; j++) {
            for (int i = 1; i < data[j].length; i++) {
                DataAnswer dataAnswer = new DataAnswer();
                dataAnswer.setTheme(data[0][0]);
                dataAnswer.setAnswer(data[j][0]);
                dataAnswer.setProblemenglish(data[j][i++]);
                dataAnswer.setProblemchinese(data[j][i]);
                dataAnswer.save();
            }
        }
    }



    //主题
    protected void datatheme(String[][] data) {

        for (int j = 1; j < data.length; j++) {
            DataTheme dataTheme = new DataTheme();
            dataTheme.setNameenglish(data[j][0]);
            dataTheme.setNamechinese(data[j][1]);
            dataTheme.setProblem(data[j][2]);
            dataTheme.setTheme(data[j][3]);
            dataTheme.setType(data[0][0]);
            dataTheme.setTypes(data[0][1]);
            dataTheme.setImageId(Integer.parseInt(data[j][4]));
            dataTheme.save();
        }
    }

    //每日英语
    protected void dailyenglish(String[][] data) {

        for (int j = 0; j < data.length; j++) {
            DailyEnglish dailyEnglish = new DailyEnglish();
            dailyEnglish.setElementenglish(data[j][0]);
            dailyEnglish.setElementchinese(data[j][1]);
            dailyEnglish.setImageId(Integer.parseInt(data[j][2]));
            dailyEnglish.save();
        }
    }

    //寄存器
    protected void dataregister(String[] data) {

        for (int j = 0; j < data.length; j++) {
            Register register = new Register();
            register.setName(data[j]);
            register.setElement(data[++j]);
            register.save();

        }
    }
}
