package com.example.orderapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ViewHolder>{

    String TAG = "DetailListAdapter";
    List<MyItem> dataList;
    Context mContext;
    float total_price;

    public DetailListAdapter(Context context, List<MyItem> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_item,parent,false);
        DetailListAdapter.ViewHolder holder = new DetailListAdapter.ViewHolder(view);
        total_price = 0;
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyItem myItem = dataList.get(position);
        holder.detail_item_title.setText(myItem.getName());
        holder.detail_item_price.setText("$: "+myItem.getPrice());
        holder.detail_item_img.setImageResource(myItem.getItem_img());
        holder.detail_item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 選購");

                Intent intent = new Intent();
                intent.setClass(mContext,CustomizationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",myItem.getName());
                bundle.putFloat("Price",myItem.getPrice());
                bundle.putInt("Image",myItem.getItem_img());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                /*Log.d(TAG, "onClick: "+myItem.getName());
                Log.d(TAG, "onClick: "+myItem.getPrice());
                Intent intent_Broadcast = new Intent();
                intent_Broadcast.setAction("Add_Item");
                mContext.sendBroadcast(intent_Broadcast);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView detail_item_img;
        TextView detail_item_title;
        LinearLayout detail_item_click;
        TextView detail_item_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detail_item_title = itemView.findViewById(R.id.detail_item_title);
            detail_item_click = itemView.findViewById(R.id.detail_item_click);
            detail_item_price = itemView.findViewById(R.id.detail_item_price);
            detail_item_img = itemView.findViewById(R.id.detail_item_img);

        }
    }
}
