package com.example.xiaopeng.speechsynthesis;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.aip.asrwakeup3.core.util.MyLogger;

import com.example.xiaopeng.R;
import com.example.xiaopeng.data.data.Register;
import com.example.xiaopeng.xiaopengspeak.XiaoPengSpeechSynthesis;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XiaoPengVoiceRecognition extends XiaoPengSpeechSynthesis {


    protected boolean enableOffline;
    protected MyRecognizer myRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            register();



    }


    protected void register(){
        Handler handler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }
        };
        MyLogger.setHandler(handler);
        // 基于DEMO集成第1.1, 1.2, 1.3 步骤 初始化EventManager类并注册自定义输出事件
        // DEMO集成步骤 1.2 新建一个回调类，识别引擎会回调这个类告知重要状态和识别结果
        IRecogListener listener = new MessageStatusRecogListener(handler);
        // DEMO集成步骤 1.1 1.3 初始化：new一个IRecogListener示例 & new 一个 MyRecognizer 示例,并注册输出事件
        myRecognizer = new MyRecognizer(this, listener);
    }

    protected void start(int startlanguage){
        // DEMO集成步骤2.1 拼接识别参数： 此处params可以打印出来，直接写到你的代码里去，最终的json一致即可。
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sound_start",         R.raw.bdspeech_recognition_start );
        params.put("sound_success",       R.raw.bdspeech_recognition_success);
        params.put("sound_error",         R.raw.bdspeech_recognition_error );
        params.put("sound_cancel",        R.raw.bdspeech_recognition_cancel);
        params.put("sound_end",           R.raw.bdspeech_speech_end );
        params.put("pid",                 startlanguage       );
        params.put("accept-audio-volume", "false"      );
        // 复制此段可以自动检测常规错误
        (new AutoCheck(getApplicationContext(), new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                    }
                }
            }
        }, enableOffline)).checkAsr(params);
        // 这里打印出params， 填写至您自己的app中，直接调用下面这行代码即可。
        // DEMO集成步骤2.2 开始识别
        try {
            myRecognizer.start(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void cancel() {
        try {
            myRecognizer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void stops() {

        myRecognizer.stop();
    }

    protected void handleMsg(Message msg) {
    }
}
