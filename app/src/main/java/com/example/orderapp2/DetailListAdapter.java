package com.example.orderapp2;

import android.content.Context;
import android.content.Intent;
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
                //Toast.makeText(mContext, "選購", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: 選購");
                /*Log.d(TAG, "onClick: total_price = "+total_price);
                Log.d(TAG, "onClick: total_price = "+total_price);
                Intent intent = new Intent();
                intent.setAction("key");
                Bundle bundle = new Bundle();
                //bundle.putString("price",""+mFruit.getPrice());
                bundle.putString("name",myItem.getName());
                //bundle.putFloat("price",mFruit.getPrice());
                bundle.putFloat("price",myItem.getPrice());
                bundle.putInt("position",holder.getAdapterPosition());
                bundle.putInt("flag",2);
                intent.putExtra("msg1",bundle);
                mContext.sendBroadcast(intent);*/
                Intent intent = new Intent();
                intent.setClass(mContext,CustomizationActivity.class);
                mContext.startActivity(intent);

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
