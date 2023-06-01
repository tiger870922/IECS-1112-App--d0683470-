package com.example.orderapp2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class BuyCarListAdapter extends RecyclerView.Adapter<BuyCarListAdapter.ViewHolder>{

    String TAG = "BuyCarListAdapter";
    List<BuyCarItem> dataList;
    Context mContext;
    float total_price;
    int amount=1;
    int price = 0;
    BuyCarDBHelper buyCarDBHelper;
    SQLiteDatabase buyCardbrw;
    private int[] index_price = new int[50];
    private int[] index_amount = new int[50];

    public BuyCarListAdapter(Context context, List<BuyCarItem> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }


    @Override
    public BuyCarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buycar_item,parent,false);
        BuyCarListAdapter.ViewHolder holder = new BuyCarListAdapter.ViewHolder(view);
        buyCarDBHelper = new BuyCarDBHelper(mContext);
        buyCardbrw = buyCarDBHelper.getWritableDatabase();
        total_price = 0;
        return holder;
    }

    @Override
    public void onBindViewHolder(BuyCarListAdapter.ViewHolder holder, int position) {
        holder.img_buycarlist.setImageResource(dataList.get(position).getItem_img());
        holder.img_buycarlist_name.setText(dataList.get(position).getName());
        holder.img_buycarlist_note.setText(dataList.get(position).getNote());
        index_price[position] = (int) dataList.get(position).getPrice();
        index_amount[position] = (int) dataList.get(position).getAmount();
        holder.txt_buycarlist_amount.setText(""+index_amount[position]);
        holder.txt_buycarlist_price.setText("$"+(index_price[position]*index_amount[position]));
        holder.img_buycarlist_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                price = index_price[position];
                amount = index_amount[position];
                amount++;
                index_amount[position] = amount;
                Log.d(TAG, "onClick: position"+position);
                UpdateCount(dataList.get(position).getName(),amount);
                sendBroadcast();
            }
        });

        holder.img_buycarlist_mini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                price = index_price[position];
                amount = index_amount[position];
                amount--;
                index_amount[position] = amount;
                if(amount<=0){
                    Log.d(TAG, "amount==0 ");
                    amount = 0;
                    buyCardbrw.delete("buyCarTable","name="+"'"+dataList.get(position).getName()+"'",null);
                    notifyItemRemoved(position);
                    dataList.remove(position);
                    notifyDataSetChanged();
                }else {
                    UpdateCount(dataList.get(position).getName(),amount);
                }
                sendBroadcast();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_buycarlist;
        TextView img_buycarlist_name;
        TextView img_buycarlist_note;
        ImageView img_buycarlist_mini;
        ImageView img_buycarlist_add;
        TextView txt_buycarlist_amount;
        TextView txt_buycarlist_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_buycarlist_mini = itemView.findViewById(R.id.img_buycarlist_mini);
            img_buycarlist_add = itemView.findViewById(R.id.img_buycarlist_add);
            txt_buycarlist_amount = itemView.findViewById(R.id.txt_buycarlist_amount);
            txt_buycarlist_price = itemView.findViewById(R.id.txt_buycarlist_price);
            img_buycarlist = itemView.findViewById(R.id.img_buycarlist);
            img_buycarlist_name = itemView.findViewById(R.id.img_buycarlist_name);
            img_buycarlist_note = itemView.findViewById(R.id.img_buycarlist_note);

        }
    }

    private void UpdateCount(String item_name,int count){
        ContentValues cv = new ContentValues();
        cv.put("amount",count);
        buyCardbrw.update("buyCarTable",cv,"name="+"'"+item_name+"'",null);
    }

    private void sendBroadcast(){
        Intent intent_Broadcast = new Intent();
        intent_Broadcast.setAction("Update_Price");
        mContext.sendBroadcast(intent_Broadcast);
    }
}
