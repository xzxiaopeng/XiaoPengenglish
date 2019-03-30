package com.example.xiaopeng.chatwindow;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.xiaopeng.R;
import com.example.xiaopeng.data.data.DailyEnglish;
//import com.example.xiaopeng.data.data.DataAnswer;
import com.example.xiaopeng.data.data.DataAnswer;
import com.example.xiaopeng.data.data.DataProblem;
import com.example.xiaopeng.data.data.DataTheme;
import com.example.xiaopeng.data.data.Register;
import com.example.xiaopeng.speechsynthesis.XiaoPengVoiceRecognition;
import com.example.xiaopeng.xiaopengspeak.XiaoPengSpeechSynthesis;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XiaoPengChatWindow extends XiaoPengVoiceRecognition {
    private List<Msg> msgList = new ArrayList<>();
    private List<Fruit> fruitList = new ArrayList<>();
    private EditText inputText;
    private int textdelete = 0;
    private ImageButton send;
    protected ImageButton btn;
    private RecyclerView msgRecyclerView;
    private RecyclerView answerview;
    private SwipeRefreshLayout swipeRefresh;
    private MsgAdapter adapter;
    private FruitAdapter fruitadapter;
    private String string1;
    private String string2;
    private String speak;
    private int speakint = 0;
    private int startlanguage = 1737;
    private int startint = 0;
    private int onscroll = 0;

    private String regEx = "[\u4e00-\u9fa5]";
    private Pattern pat = Pattern.compile(regEx);
    private String[] datachinese;
    private String string3;

    private String nextsentence = null;
    private String theme = null;
    private int number = 0;


    private int actiondown = 0;
    private int actionup = 0;
    private int actionmove = 0;
    private int egetx = 0;
    private int egety = 0;
    private int ynumber = 0;
    private int xnumber = 0;
    private String updown = "";
    private String leftright = "";

    private int numbers = 0;
    private int actiondowns = 0;
    private int actionups = 0;
    private int actionmoves = 0;
    private int egetxs = 0;
    private int egetys = 0;
    private int ynumbers = 0;
    private int xnumbers = 0;
    private String updowns = "";
    private String leftrights = "";

    private int ok = 0;
    private String butt = "start";


    boolean isSlidingToLast = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatwindow_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        initMsgs(); // 初始化消息数据
        initFruits(); // 初始化水果数据
        inputText = (EditText) findViewById(R.id.input_text);
        send = (ImageButton) findViewById(R.id.send);
        btn = (ImageButton) findViewById(R.id.btn);

        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        answerview = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        but();
        inputtext();
        recyclerview();
        initPermission();


        LinearLayoutManager layoutManagers = new LinearLayoutManager(this);
        answerview.setLayoutManager(layoutManagers);
        fruitadapter = new FruitAdapter(fruitList);
        answerview.setAdapter(fruitadapter);

        answerviews();
    }

    private void answerviews() {
        answerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN://0
                        egetxs = (int) e.getX();
                        egetys = (int) e.getY();
                        actiondowns++;
                        Log.e("TAG", "LinearLayout onTouch按住");
                        break;
                    case MotionEvent.ACTION_UP://1
                        actionups++;
                        Log.e("TAG", "LinearLayout onTouch抬起");
                        break;
                    case MotionEvent.ACTION_MOVE://2
                        ActionMoves((int) e.getY(), (int) e.getX());
                        actionmoves++;
                        Log.e("TAG", "LinearLayout onTouch移动");
                        break;
                    default:
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

    }

    private void ActionMoves(int gety, int getx) {
        if ((gety - egetys) <= 0) {
            ynumbers = egetys - gety;
        } else {
            ynumbers = gety - egetys;
        }

        if (ynumbers > 300) {
            if (gety < egetys) {
                updowns = "up";
            } else {
                updowns = "down";
                if (ynumbers > 400 && numbers == 0) {
                    numbers++;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            numbers = 0;
                        }
                    }).start();
                    updowns = "";
                    if (answerview.getVisibility() == View.VISIBLE) {
                        msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
                        answerview.setVisibility(View.GONE);//隐藏提示窗口
                    }
                }
            }
        }


        if ((getx - egetxs) >= 0) {
            xnumbers = getx - egetxs;
        } else {
            xnumbers = egetxs - getx;
        }
        if (xnumbers > 350) {
            if (xnumbers > 390) {
                if (getx < egetx) {
                    //大于0表示正在向右滚动
                    leftrights = "left";
                } else {
                    //小于等于0表示停止或向左滚动
                    leftrights = "right";
                    finish();
                }
            } else {
                if (getx < egetxs) {
                    //大于0表示正在向右滚动
                    leftrights = "left";
                } else {
                    //小于等于0表示停止或向左滚动
                    leftrights = "right";
                }
            }
        }
    }

    @Override
    protected void onResume() {
        if ("晓鹏".equals(getJcRegisters("输入法"))) {
            startlanguage = 1536;
            inputText.setHint("晓鹏");
            setJcRegisters("输入法", "晓鹏");
        }

        if (ok == 0) {
            InputMethodManager imm = (InputMethodManager) inputText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);

            inputText.setFocusable(true);
            inputText.setFocusableInTouchMode(true);
            inputText.requestFocus();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    }
                });
            }
        }).start();
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (ok == 0) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        ok++;


        super.onStop();
    }

    private void recyclerview() {
        msgRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN://0
                        egetx = (int) e.getX();
                        egety = (int) e.getY();
                        actiondown++;
                        Log.e("TAG", "LinearLayout onTouch按住");
                        break;
                    case MotionEvent.ACTION_UP://1
                        View childView = msgRecyclerView.findChildViewUnder(e.getX(), e.getY());
                        ActionUp(childView);
                        actionup++;
                        Log.e("TAG", "LinearLayout onTouch抬起");
                        break;
                    case MotionEvent.ACTION_MOVE://2
                        ActionMove((int) e.getY(), (int) e.getX());
                        actionmove++;
                        Log.e("TAG", "LinearLayout onTouch移动");
                        break;
                    default:
                }

                return false;


            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        // 实现上拉加载重要步骤，设置滑动监听器，RecyclerView自带的ScrollListener
        msgRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 在newState为滑到底部时
//                recyclerView.canScrollVertically(-1);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && "up".equals(updown) && ynumber > 600) {
                        updown = "";
//                        加载更多功能的代码
                        if (answerview.getVisibility() == View.GONE) {
                            msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
                            answerview.setVisibility(View.VISIBLE);//显示提示窗口
                            answerview.scrollToPosition(0);
                        } else {
                            msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
                            answerview.setVisibility(View.GONE);//显示提示窗口
                            answerview.scrollToPosition(0);
                        }
                    }
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    //大于0表示正在向右滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向左滚动
                    isSlidingToLast = false;
                }
            }
        });
    }

    private void ActionUp(View childView) {
        if (childView != null) {
            int position = msgRecyclerView.getChildLayoutPosition(childView);
            Msg fruit = msgList.get(position);
            speak = fruit.getContent();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(450);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (actiondown == 1 && actiondown == 1 && actionmove == 0) {
                                if (butt.equals("cancel")) {
                                    butt = "start";
                                    startint = 0;
                                    cancel();
                                }
                                onscroll = 0;
                                speak(speak);
                                speak = "";
                                stop();
                            }
                            actiondown = 0;
                            actiondown = 0;
                            actionmove = 0;
                        }
                    });
                }
            }).start();
        }
    }

    private void ActionMove(int gety, int getx) {
        if ((gety - egety) <= 0) {
            ynumber = egety - gety;
        } else {
            ynumber = gety - egety;
        }

        if (ynumber > 350) {
            if (gety < egety) {
                updown = "up";
            } else {
                updown = "down";
                if (ynumber > 1200 && number == 0) {
                    number++;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            number = 0;
                        }
                    }).start();
                    updown = "";
//                    refreshFruits();
                }
            }
        }


        if ((getx - egetx) >= 0) {
            xnumber = getx - egetx;
        } else {
            xnumber = egetx - getx;
        }
        if (xnumber > 350) {
            if (xnumber > 650) {
                if (getx < egetx) {
                    //大于0表示正在向右滚动
                    leftright = "left";
                } else {
                    //小于等于0表示停止或向左滚动
                    leftright = "right";
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    finish();
                }
            } else {
                if (getx < egetx) {
                    //大于0表示正在向右滚动
                    leftright = "left";
                } else {
                    //小于等于0表示停止或向左滚动
                    leftright = "right";
                }
            }
        }
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initMsgs();
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    }
                });
            }
        }).start();
    }

    private void but() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buts();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buts();
            }

        });
        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textdelete++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(450);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textdelete = 0;
                                msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
                            }
                        });
                    }
                }).start();
                if (textdelete == 1) {
                    stop();
                }
                if (textdelete == 2 && inputText.getText().toString() != null) {
                    inputText.setText("");
                    textdelete = 0;
                }
            }
        });
    }

    private void buts() {
        String content = inputText.getText().toString();
        if (!"".equals(content)) {
            input(content);
        } else {
            switch (butt) {
                case "start":
                    if (startint == 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        starts();
                                    }
                                });
                            }
                        }).start();
                        startint++;
                    } else {
                        if (startint < 30) {
                            startint++;
                        } else {
                            startint = 4;
                        }
                        if (startint == 2) {
                            if ("晓鹏".equals(inputText.getHint())) {
                                startlanguage = 1737;
                                inputText.setHint("XiaoPeng.");
                                setJcRegisters("输入法", "XiaoPeng.");
                            } else {
                                startlanguage = 1536;
                                inputText.setHint("晓鹏");
                                setJcRegisters("输入法", "晓鹏");
                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    startint = 0;
                                }
                            }).start();
                        }
                    }
                    break;
                case "cancel":
                    startint = 0;
                    cancel();
                    butt = "start";
                    break;
                default:
                    break;
            }
        }
    }

    protected void input(String content) {

        int r = 0;
        String data = content;
        Matcher matcher = pat.matcher(content);
        content = content.replaceAll("[\\p{P}\\p{Punct}]", "");
        if (matcher.find()) {
            List<DataProblem> dataProblems = LitePal.where("problem == ? and theme == ?", nextsentence, theme).find(DataProblem.class);
            for (DataProblem dataProblem : dataProblems) {

                datachinese = dataProblem.getProblemchinese().replaceAll("[\\p{P}\\p{Punct}]", "").split("|");
                r = 0;
                for (int i = 1; i < datachinese.length; i++) {
                    string3 = content.replaceAll(datachinese[i], "");
                    if (string3.equals(content)) {
                    } else {

                        r++;

                    }
                }

                if (r >= dataProblem.getAnswerchinesenumber()) {
                    butt = "start";
                    Msg msg = new Msg(data, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时， 刷新RecyclerView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                    nextstatement(dataProblem.getNext());
                    break;
                }
            }
        } else {
            content = content.toLowerCase();
            List<DataProblem> dataProblems = LitePal.where("problem == ? and theme == ?", nextsentence, theme).find(DataProblem.class);
            for (DataProblem dataProblem : dataProblems) {

//               if(dataProblem.getProblemenglish()||dataProblem.getProblemchinese()){
                datachinese = dataProblem.getProblemenglish().toLowerCase().replaceAll("[\\p{P}\\p{Punct}]", "").split(" ");
                r = 0;
                for (int i = 0; i < datachinese.length; i++) {
                    string3 = content.replaceAll(datachinese[i], "");
                    if (string3.equals(content)) {
                    } else {
                        if(datachinese[i].length()>1){
                            r++;
                        }
                    }
                }

                if (r >= dataProblem.getAnswerenglishnumber()) {
                    butt = "start";
                    Msg msg = new Msg(data, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时， 刷新RecyclerView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                    nextstatement(dataProblem.getNext());
                    break;
                }
            }
        }
    }

    protected void nextstatement(String string) {
        List<DataAnswer> dataAnswers = LitePal.where("answer == ?and theme == ?", string, theme).find(DataAnswer.class);
        //产生一个100以内的整数:int x=(int)(Math.random()*100);产生一个>>>以内的整数包括0:((int) (1 + Math.random() * (xiaoPengDataSentence.size() - 1))-1)
        int numbers = ((int) (1 + Math.random() * (dataAnswers.size() - 1)) - 1);
        if (dataAnswers.size() == 0) {
            nextsentence = "";
            initFruits();
            msgRecyclerView.scrollToPosition(msgList.size() - 1);
        }

        if (numbers > dataAnswers.size()) {
            numbers = dataAnswers.size();
        }

        for (DataAnswer dataAnswer : dataAnswers) {
            string = dataAnswer.getAnswer();
            if ((numbers--) < 1) {
                nextsentence = dataAnswer.getAnswer();
                speak(dataAnswer.getProblemenglish());
                if (!"".equals(dataAnswer.getProblemenglish())) {
                    Msg msg1 = new Msg("  " + dataAnswer.getProblemenglish(), Msg.TYPE_RECEIVED);
                    msgList.add(msg1);
                    Msg msg2 = new Msg("  " + dataAnswer.getProblemchinese(), Msg.TYPE_RECEIVED);
                    msgList.add(msg2);
                }

                initFruits();
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
                break;
            }
        }
    }


    protected void starts() {
        if (startint == 1) {
            stop();
            start(startlanguage);
            butt = "cancel";
        }
    }

    @Override
    protected void handleMsg(Message msg) {

        switch (msg.what) { // 处理MessageStatusRecogListener中的状态回调
            case 6:
                if (msg.arg2 == 1) {
                    butt = "start";
                    startint = 0;
                    string3 = (String) msg.obj;
                    // msg 储存识别的参数；s = s.replaceAll("\r", "") 去掉\r;
                    switch (string3) {
                        case "识别错误、。，‘\n":
                            string3 = "";
                            break;
                        default:
                            break;
                    }

                    inputText.setText(string3.replaceAll("\n", ""));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        inputText.setSelection(string3.replaceAll("\n", "").length());//将光标移至文字末尾
                                    } catch (Exception e) {
                                    }
                                }
                            });
                        }
                    }).start();

                }
                break;
            default:
                break;
        }
    }

    private void inputtext() {

        inputText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                stop();
                //text  输入框中改变后的字符串信息
                if ("".equals(string1)) {
                    cancel();
                    butt = "start";
                    if ("\n".equals(text.toString())) {
                        inputText.setText("");
                    }
                } else {
                    try {
                        string2 = text.toString().replaceAll(string1, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if ("\n".equals(string2)) {
                        String content = inputText.getText().toString().replaceAll("\n", "");
                        if (!"".equals(content)) {
                            input(content);

                        }
                    }
                }
                //start 输入框中改变后的字符串的起始位置
                //before 输入框中改变前的字符串的位置 默认为0
                //count 输入框中改变后的一共输入字符串的数量
            }

            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                //text  输入框中改变前的字符串信息
                string1 = "";

                string1 = text.toString();

                //start 输入框中改变前的字符串的起始位置
                //count 输入框中改变前后的字符串改变数量一般为0
                //after 输入框中改变后的字符串与起始位置的偏移量
            }

            @Override
            public void afterTextChanged(Editable edit) {
                //edit  输入结束呈现在输入框中的信息

            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    void initFruits() {
        String r = "";
        while (fruitList.size() > 0&&fruitadapter != null) {
            try {
                fruitadapter.removeData(fruitList.size() - 1);
            } catch (Exception e) {

            }
        }
        Fruit mangosz = new Fruit("", 0);
        fruitList.add(mangosz);

        List<DataProblem> dataProblems = LitePal.where("problem == ? and theme == ?", nextsentence, theme).find(DataProblem.class);
        //产生一个100以内的整数:int x=(int)(Math.random()*100);产生一个>>>以内的整数包括0:((int) (1 + Math.random() * (xiaoPengDataSentence.size() - 1))-1)
        for (DataProblem dataProblem : dataProblems) {

                if ("#####".equals(dataProblem.getProblemenglish())) {
                r = "1";
                nextstatements(dataProblem.getNext());
                break;
            }
        }
        if (r.equals("")) {
            for (DataProblem dataProblem : dataProblems) {
                Fruit mangos = new Fruit(dataProblem.getProblemenglish() + "\n" + dataProblem.getProblemchinese(), 0);
                fruitList.add(mangos);
            }
        }

        for (int i = 0; i < 6; i++) {
            Fruit mangoss = new Fruit("", 0);
            fruitList.add(mangoss);
        }
    }

    protected void nextstatements(String string) {
        List<DataAnswer> dataAnswers = LitePal.where("answer == ?and theme == ?", string, theme).find(DataAnswer.class);
        //产生一个100以内的整数:int x=(int)(Math.random()*100);产生一个>>>以内的整数包括0:((int) (1 + Math.random() * (xiaoPengDataSentence.size() - 1))-1)
        int numbers = ((int) (1 + Math.random() * (dataAnswers.size() - 1)) - 1);
        if (dataAnswers.size() == 0) {
            nextsentence = "";
            initFruits();
            msgRecyclerView.scrollToPosition(msgList.size() - 1);
        }

        if (numbers > dataAnswers.size()) {
            numbers = dataAnswers.size();
        }

        for (DataAnswer dataAnswer : dataAnswers) {
            string = dataAnswer.getAnswer();
            if ((numbers--) < 1) {
                nextsentence = dataAnswer.getAnswer();
                speak(dataAnswer.getProblemenglish());
                if (!"".equals(dataAnswer.getProblemenglish())) {
                    Msg msg1 = new Msg("  " + dataAnswer.getProblemenglish(), Msg.TYPE_RECEIVED);
                    msgList.add(msg1);
                    Msg msg2 = new Msg("  " + dataAnswer.getProblemchinese(), Msg.TYPE_RECEIVED);
                    msgList.add(msg2);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    initFruits();
                                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                                } catch (Exception e) {
                                }
                            }
                        });
                    }
                }).start();

                break;
            }
        }
    }

    void initMsgs() {

        String strings = "";

        for (int i = 0; i < 20; i++) {
            Msg msg = new Msg("", Msg.TYPE_RECEIVED);
            msgList.add(msg);
        }

        List<DataAnswer> dataAnswers = LitePal.where("answer == ? and theme == ?", getJcRegisters("传递"), getJcRegisters("主题")).find(DataAnswer.class);
        //产生一个100以内的整数:int x=(int)(Math.random()*100);产生一个>>>以内的整数包括0:((int) (1 + Math.random() * (xiaoPengDataSentence.size() - 1))-1)
        int numbers = ((int) (1 + Math.random() * (dataAnswers.size() - 1)) - 1);
        if (numbers == Integer.parseInt(getJcRegisters("重复主题"))) {
            numbers++;
        }
        if (numbers > dataAnswers.size()) {
            numbers = dataAnswers.size();
        }
        setJcRegisters("重复主题", numbers + "");
        for (DataAnswer dataAnswer : dataAnswers) {
            if ((numbers--) <= 1) {
                nextsentence = dataAnswer.getAnswer();
                theme = dataAnswer.getTheme();
                if(!dataAnswer.getProblemenglish().equals("")){
                    Msg msg1 = new Msg("  " + dataAnswer.getProblemenglish(), Msg.TYPE_RECEIVED);
                    speak(dataAnswer.getProblemenglish());
                    msgList.add(msg1);
                    Msg msg2 = new Msg("  " + dataAnswer.getProblemchinese(), Msg.TYPE_RECEIVED);
                    msgList.add(msg2);
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {

        // 如果之前调用过myRecognizer.loadOfflineEngine()， release()里会自动调用释放离线资源
        // 基于DEMO5.1 卸载离线资源(离线时使用) release()方法中封装了卸载离线资源的过程
        // 基于DEMO的5.2 退出事件管理器
        myRecognizer.release();
        super.onDestroy();
    }
}
