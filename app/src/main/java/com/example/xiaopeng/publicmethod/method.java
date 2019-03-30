package com.example.xiaopeng.publicmethod;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.xiaopeng.R;
import com.example.xiaopeng.data.XiaoPengData;
import com.example.xiaopeng.data.data.DataTheme;
import com.example.xiaopeng.data.data.Register;
import com.example.xiaopeng.xiaopengactivity.MainActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class method extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //向寄存器查询数据。
    protected String getJcRegisters(String data){

            List<Register> registers = LitePal.where("name == ?",data).find(Register.class);
            for(Register register:registers) {
                data = register.getElement();
            }

        return  data;
    }




    //向寄存器存入数据：names寄存器名字，data寄存的数据。
    protected void setJcRegisters(String names,String data){
        Register register = new Register();
        register.setElement(data);
        register.updateAll("name == ?",names);
    }



    /**
     * android 6.0 以上需要动态申请权限
     */
    protected void initPermission() {

        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.RECORD_AUDIO
        };

        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

}
