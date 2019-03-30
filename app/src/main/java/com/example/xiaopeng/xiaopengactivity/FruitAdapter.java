package com.example.xiaopeng.xiaopengactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.xiaopeng.R;
import com.example.xiaopeng.chatwindow.XiaoPengChatWindow;
import com.example.xiaopeng.data.data.DataTheme;
import com.example.xiaopeng.data.data.Register;

import org.litepal.LitePal;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{

    private static final String TAG = "FruitAdapter";

    private Context mContext;

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.xiaopeng_fruit_image);
            fruitName = (TextView) view.findViewById(R.id.xiaopeng_fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.xiaopeng_activity_fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                String[] data = fruit.getName().split("\n");

                List<DataTheme> dataThemes = LitePal.where("nameenglish = ?",data[0]+"\n"+data[1]).find(DataTheme.class);
                for(DataTheme dataTheme:dataThemes) {
                    String day = dataTheme.getProblem();
                    if(!"".equals(day)&&!day.equals(null)){
                        Register register = new Register();
                        register.setElement(dataTheme.getProblem());
                        register.updateAll("name == ?","传递");

                        Register registers = new Register();
                        registers.setElement(dataTheme.getTheme());
                        registers.updateAll("name == ?","主题");

                        Intent intent = new Intent(mContext, XiaoPengChatWindow.class);
                        mContext.startActivity(intent);
                        break;
                    }

                }

            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

}
