package com.example.xiaopeng.xiaopengactivity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import com.example.xiaopeng.R;
import com.example.xiaopeng.data.XiaoPengData;
import com.example.xiaopeng.data.data.DailyEnglish;
import com.example.xiaopeng.data.data.DataAnswer;
import com.example.xiaopeng.data.data.DataProblem;
import com.example.xiaopeng.data.data.DataTheme;
import com.example.xiaopeng.data.dialogue.XiaoPengAnswer;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends XiaoPengData {

    private DrawerLayout mDrawerLayout;


    private List<Fruit> fruitList = new ArrayList<>();
    private RecyclerView recyclerView;

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
    private Fruit fruitss;

    private String strings = "";

    private FruitAdapter adapter;

//    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaopeng_activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.xiaopeng_drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initFruits();
        initPermission();



//        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
//        answerview = (RecyclerView) findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        msgRecyclerView.setLayoutManager(layoutManager);
//        adapter = new MsgAdapter(msgList);
//        msgRecyclerView.setAdapter(adapter);


        recyclerView = (RecyclerView) findViewById(R.id.xiaopeng_recycler_view);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        recyclerview();
        //下拉刷新
//        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.xiaopeng_swipe_refresh);
//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshFruits();
//            }
//        });
    }

    //下拉刷新
//    private void refreshFruits() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(0);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        initFruits();
//                        adapter.notifyDataSetChanged();
//                        swipeRefresh.setRefreshing(false);
//                    }
//                });
//            }
//        }).start();
//    }

    private void initFruits() {
        int sizes=0;
        fruitList.clear();
        setJcRegisters("第几天",(Integer.parseInt(getJcRegisters("第几天"))+1)+"");
        strings = getJcRegisters("第几天") + " St Open ";

        List<DataProblem> dataProblems = LitePal.findAll(DataProblem.class);
        int numbers =((int) (1 + Math.random() * (dataProblems.size() - 1))-1);
        if(numbers==0){numbers=1;}
        for(DataProblem dataProblem:dataProblems) {
            if ((numbers--) <= 5 && dataProblem.getProblemenglish().length()>5 && dataProblem.getProblemenglish().length() < 28) {
                if (!"".equals(dataProblem.getProblemenglish())) {
                    strings +="\n" +  dataProblem.getProblemenglish();
                }
                if (!"".equals(dataProblem.getProblemchinese())) {
                    strings += "\n" + dataProblem.getProblemchinese();
                }
                List<DataTheme> dataThemes = LitePal.where("theme == ? ", dataProblem.getTheme()).find(DataTheme.class);
                for(DataTheme dataTheme:dataThemes) {
                    fruitss = new Fruit(strings, dataTheme.getImageId());
                    fruitList.add(fruitss);
                    break;
                }
                List<DataAnswer> dataProblemss = LitePal.where("theme == ? ", dataProblem.getTheme()).find(DataAnswer.class);
                sizes=dataProblemss.size();
                strings="";
                break;
            }
        }


        strings = getJcRegisters("第几天") + " St Open ";
        List<DataAnswer> dataAnswers = LitePal.findAll(DataAnswer.class);
        numbers =((int) (1 + Math.random() * (dataAnswers.size() - sizes))-1) + sizes+1;

        if(numbers<sizes){
            numbers=dataAnswers.size()+((int) (1 + Math.random() * (10 - 1))-1);
        }
        if(numbers>dataAnswers.size()){
            numbers=dataAnswers.size()-((int) (1 + Math.random() * (10 - 1))-1);
        }
        for(DataAnswer dataAnswer:dataAnswers) {
            if ((numbers--) <= 5 && dataAnswer.getProblemenglish().length()>5 &&dataAnswer.getProblemenglish().length()<28) {
                if (!"".equals(dataAnswer.getProblemenglish())) {
                    strings +="\n" +  dataAnswer.getProblemenglish();
                }
                if (!"".equals(dataAnswer.getProblemchinese())) {
                    strings += "\n" + dataAnswer.getProblemchinese();
                }

                List<DataTheme> dataThemes = LitePal.where("theme == ? ", dataAnswer.getTheme()).find(DataTheme.class);
                for(DataTheme dataTheme:dataThemes) {
                    fruitss = new Fruit(strings, dataTheme.getImageId());
                    fruitList.add(fruitss);
                    break;
                }
                strings="";
                break;
            }
        }




        List<DataTheme> dataThemes = LitePal.where("type == ? and types == ? ", "晓鹏","晓鹏").find(DataTheme.class);
        for(DataTheme dataTheme:dataThemes) {
            strings="";
            if (!"".equals(dataTheme.getNameenglish())){
                strings = dataTheme.getNameenglish() ;
            }
            if (!"".equals(dataTheme.getNamechinese())){
                strings += "\n"+dataTheme.getNamechinese();
            }
            fruitss = new Fruit(strings, dataTheme.getImageId());
            fruitList.add(fruitss);

        }

//        for (int i = 0; i < 50; i++) {
//            Random random = new Random();
//            int index = random.nextInt(fruits.length);
//            fruitList.add(fruits[index]);
//        }
    }

    @Override
    protected void onResume() {


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
                        recyclerView.scrollToPosition(2);
                    }
                });
            }
        }).start();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
    }

    private void recyclerview() {
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                        if (answerview.getVisibility() == View.GONE) {
//                            msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
//                            answerview.setVisibility(View.VISIBLE);//显示提示窗口
//                            answerview.scrollToPosition(0);
//                        }else{
//                            msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将RecyclerView定位到最后一行
//                            answerview.setVisibility(View.GONE);//显示提示窗口
//                            answerview.scrollToPosition(0);
//                        }
                    }
                }
            }


//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//                if (dy > 0) {
//                    //大于0表示正在向右滚动
//                    isSlidingToLast = true;
//                } else {
//                    //小于等于0表示停止或向左滚动
//                    isSlidingToLast = false;
//                }
//            }
        });
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



}

