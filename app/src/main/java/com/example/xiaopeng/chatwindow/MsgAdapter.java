package com.example.xiaopeng.chatwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaopeng.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>  {

    private Context mContext;
    private List<Msg> mMsgList;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        public ViewHolder(View view) {
            super(view);
            itemView=view;
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
        }
    }
    public MsgAdapter(List<Msg> msgList) { mMsgList = msgList;    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);

        final MsgAdapter.ViewHolder holder = new MsgAdapter.ViewHolder(view);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Msg fruit = mMsgList.get(position);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Msg fruit = mMsgList.get(position);
            }
        });

        return holder;
    }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED) {            // 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
             holder.leftLayout.setVisibility(View.VISIBLE);
             holder.rightLayout.setVisibility(View.GONE);

             holder.leftMsg.setText(msg.getContent());
        } else if(msg.getType() == Msg.TYPE_SENT) {            // 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
             holder.rightLayout.setVisibility(View.VISIBLE);
             holder.leftLayout.setVisibility(View.GONE);

             holder.rightMsg.setText(msg.getContent());
        }
    }
    @Override
    public int getItemCount() {        return mMsgList.size(); }


}