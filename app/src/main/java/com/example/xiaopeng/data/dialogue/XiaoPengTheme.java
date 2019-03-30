package com.example.xiaopeng.data.dialogue;

import com.example.xiaopeng.R;
import com.example.xiaopeng.data.XiaoPengMethod;

public class XiaoPengTheme extends XiaoPengAnswer {
    //主题
    protected void datathemes() {
        helloXiaoPeng();
        donsleepgetup();
        shieldSeason1();
        internetbar();
    }

    private void datatheme() {
        datatheme(new  String[][] {
                {"hello, I'm Xiao Peng.","你好！我叫晓鹏！","我的名字叫晓鹏你叫什么名字", "自我介绍","晓鹏", R.mipmap.ic_launchers+""},
                {"Don't sleep. Get up.","别睡了，起床了！","起床了", "起床了","晓鹏",R.drawable.getup+""}
        });
    }

    private void helloXiaoPeng() {
        datatheme(new  String[][] {{"晓鹏","晓鹏"},
                {"hello, I'm Xiao Peng.\n ","你好！我叫晓鹏！","我的名字叫晓鹏你叫什么名字", "自我介绍", R.mipmap.ic_launchers+""},
                {"interview.\n Embarrassed interview.","面试。","你好我是受邀过来面试的。", "令人尴尬的面试", R.drawable.mianshi+""}
        });
    }

    private void internetbar() {
        datatheme(new  String[][] {{"晓鹏","晓鹏"},
                {"Xiaopeng is at the Internet cafe.\n ","晓鹏在网吧。","你好啊！", "晓鹏在网吧", R.drawable.internetbar+""}
        });
    }

    private void donsleepgetup() {
        datatheme(new  String[][] {{"晓鹏","晓鹏"},
                {"Don't sleep. Get up.\n ","别睡了，起床了！","起床了", "起床了",R.drawable.getup+""}
        });
    }

    private void shieldSeason1() {
        datatheme(new  String[][] {{"晓鹏","晓鹏"},
                {"S.H.I.E.L.D Season 01 First 01\nSkye and Mike Peterson","神盾局特工第一季  第01集","表现得自然点", "神盾局特工第一季01Skye and Mike Peterson",R.drawable.shieldseason1+""},
                {"S.H.I.E.L.D Season 01 First 01\nSkye and Agent Phil Coulson","神盾局特工第一季  第01集","我更他说，他爸爸就要回家了。", "神盾局特工第一季01Skye and Agent Phil Coulson",R.drawable.shieldseason1+""},
                {"S.H.I.E.L.D Season 01 First 02\nSkye and Agent Grant Ward","神盾局特工第一季  第02集","在看《饥饿游戏》？", "神盾局特工第一季02Skye and Agent Grant Ward",R.drawable.shieldseason1+""}
        });
    }


}
