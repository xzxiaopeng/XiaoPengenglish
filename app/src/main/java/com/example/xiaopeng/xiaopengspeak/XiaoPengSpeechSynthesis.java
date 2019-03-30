package com.example.xiaopeng.xiaopengspeak;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.example.xiaopeng.data.data.Register;
import com.example.xiaopeng.publicmethod.method;
import com.example.xiaopeng.speechsynthesis.XiaoPengVoiceRecognition;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XiaoPengSpeechSynthesis extends method {



    // ================== 初始化参数设置开始 ==========================

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private TtsMode ttsMode = TtsMode.ONLINE;

    // ================选择TtsMode.ONLINE  不需要设置以下参数; 选择TtsMode.MIX 需要设置下面2个离线资源文件的路径
    private static final String TEMP_DIR = "/sdcard/baiduTTS"; // 重要！请手动将assets目录下的3个dat 文件复制到该目录

    // 请确保该PATH下有这个文件
    private static final String TEXT_FILENAME = TEMP_DIR + "/" + "bd_etts_text.dat";

    // 请确保该PATH下有这个文件 ，m15是离线男声
    private static final String MODEL_FILENAME =
            TEMP_DIR + "/" + "bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat";

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    protected SpeechSynthesizer mSpeechSynthesizer;

    // =========== 以下为UI部分 ==================================================


    protected Handler mainHandler;



//    private static final String TAG = "MiniActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initTTs();
    }

    /**
     * 注意此处为了说明流程，故意在UI线程中调用。
     * 实际集成中，该方法一定在新线程中调用，并且该线程不能结束。具体可以参考NonBlockSyntherizer的写法
     */
    private void initTTs() {

                LoggerProxy.printable(true); // 日志打印在logcat中
                boolean isMix = ttsMode.equals(TtsMode.MIX);
                boolean isSuccess;
                if (isMix) {
                    // 检查2个离线资源是否可读
                    isSuccess = checkOfflineResources();
                    if (!isSuccess) {
                        return;
                    } else {
                    }
                }
                // 1. 获取实例
                mSpeechSynthesizer = SpeechSynthesizer.getInstance();
                mSpeechSynthesizer.setContext(XiaoPengSpeechSynthesis.this);

                // 3. 设置appId，appKey.secretKey
                mSpeechSynthesizer.setAppId("15549429");
                mSpeechSynthesizer.setApiKey("7LzN0wuDON6d24XBpeNy5Yra", "tSzeBQmyw45hkGvhg4MNfq4hp6A4tY08");

                // 4. 支持离线的话，需要设置离线模型
                if (isMix) {
                    // 检查离线授权文件是否下载成功，离线授权文件联网时SDK自动下载管理，有效期3年，3年后的最后一个月自动更新。
                    isSuccess = checkAuth();
                    if (!isSuccess) {
                        return;
                    }
                    // 文本模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
                    mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
                    // 声学模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
                    mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
                }

                // 5. 以下setParam 参数选填。不填写则默认值生效
                // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
                // 设置合成的音量，0-9 ，默认 5
                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
                // 设置合成的语速，0-9 ，默认 5
                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "6");
                // 设置合成的语调，0-9 ，默认 5
                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "7");

                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
                // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
                // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
                // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
                // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
                // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

                mSpeechSynthesizer.setAudioStreamType(AudioManager.MODE_IN_CALL);

                // x. 额外 ： 自动so文件是否复制正确及上面设置的参数
                Map<String, String> params = new HashMap<>();
                // 复制下上面的 mSpeechSynthesizer.setParam参数
                // 上线时请删除AutoCheck的调用
                if (isMix) {
                    params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
                    params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
                }

                // 6. 初始化
                mSpeechSynthesizer.initTts(ttsMode);


    }

    /**
     * 检查appId ak sk 是否填写正确，另外检查官网应用内设置的包名是否与运行时的包名一致。本demo的包名定义在build.gradle文件中
     *
     * @return
     */
    private boolean checkAuth() {
        AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查 TEXT_FILENAME, MODEL_FILENAME 这2个文件是否存在，不存在请自行从assets目录里手动复制
     *
     * @return
     */
    private boolean checkOfflineResources() {
        String[] filenames = {TEXT_FILENAME, MODEL_FILENAME};
        for (String path : filenames) {
            File f = new File(path);
            if (!f.canRead()) {
                return false;
            }
        }
        return true;
    }


    //语音合成播放
    protected void speak(String data) {

        if(data.replaceAll("Xiao Peng.","晓鹏").equals(data)){
        }else{
            data = data.replaceAll("Xiao Peng.","晓鹏");
        }
        if(data.replaceAll("HuaiHua, Hunan.","怀化 湖南").equals(data)){
        }else{
            data = data.replaceAll("HuaiHua, Hunan.","怀化 湖南");
        }
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
        // 设置合成的语速，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "3");
        // 设置合成的语调，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "6");

        final String finalData = data;

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    mSpeechSynthesizer.speak(finalData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    //语音合成停止
    protected void stop() {
        try {
            int result = mSpeechSynthesizer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //  下面是UI部分

    private void initView() {

        mainHandler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                if (msg.obj == null) {
                    abc(msg.obj+"");
                }
                abc(msg.obj+"");
            }

        };
    }
    protected void abc(String asd)  {
       String asdf = asd;
    }

    @Override
    protected void onDestroy() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stop();
            mSpeechSynthesizer.release();
            mSpeechSynthesizer = null;
        }
        super.onDestroy();
    }



}
