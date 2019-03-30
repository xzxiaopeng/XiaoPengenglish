package com.example.xiaopeng.data;

import android.os.Bundle;
import com.example.xiaopeng.R;
import com.example.xiaopeng.data.data.DailyEnglish;
import com.example.xiaopeng.data.data.DataAnswer;
import com.example.xiaopeng.data.data.DataProblem;
import com.example.xiaopeng.data.data.DataTheme;
import com.example.xiaopeng.data.data.Register;
import com.example.xiaopeng.data.dialogue.XiaoPengAnswer;
import com.example.xiaopeng.data.dialogue.XiaoPengTheme;

import org.litepal.LitePal;

public class XiaoPengData extends XiaoPengTheme {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if("1.1.1.".equals(getJcRegisters("版本编号"))){
        }else {
            if("XiaoPeng.".equals(getJcRegisters("输入法"))){
                delieetdata();
            }
            LitePal.getDatabase();
            adddata();
        }
    }

    private void register() {
        dataregister(new  String[] {
                "传递","",
                "主题","",
                "重复主题","0",
                "第几天","0",
                "版本编号","1.1.1.1"
        });
    }

    private void registerstart() {
        dataregister(new  String[] {
                "输入法","XiaoPeng."
        });
    }

    private void delieetdata() {
        LitePal.deleteAll(DailyEnglish.class);
        LitePal.deleteAll(DataAnswer.class);
        LitePal.deleteAll(DataProblem.class);
        LitePal.deleteAll(DataTheme.class);
        LitePal.deleteAll(Register.class);
    }

    private void adddata() {
        registerstart();
        dataproblems();
        datathemes();
        dataanswers();
        dailyenglishs();
        register();
    }




    //主题
    private void dailyenglishs() {
//        dailyenglish(new  String[][] {
//                {"I like eat apples.","我喜欢吃苹果", R.drawable.apple+""},
//                {"I like eat grape.","我喜欢吃葡萄", R.drawable.grape+""},
//                {"I like eat cherry.","我喜欢吃樱桃", R.drawable.cherry+""}
//        });
    }


}


