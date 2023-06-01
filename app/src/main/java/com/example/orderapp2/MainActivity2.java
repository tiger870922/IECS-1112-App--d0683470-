package com.example.orderapp2;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private String TAG = "MainActivity2";

    private Button btn_ok;
    private Button btn_keepGo;
    private RecyclerView main2_recycler;
    private LinearLayoutManager BuyCarListLayoutManager;
    private BuyCarListAdapter BuyCarListAdapter;
    private List<BuyCarItem> myItems;
    private TextView txt_main2_totalprice;

    BuyCarDBHelper buyCarDBHelper;
    SQLiteDatabase buyCardbrw;

    private int imgItem;
    private String name;
    private String note;
    private int amount;
    private int price;
    private int total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main2);
        total_price = 0;
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_keepGo = (Button) findViewById(R.id.btn_keepGo);
        main2_recycler = (RecyclerView)findViewById(R.id.main2_recycler);
        txt_main2_totalprice = (TextView)findViewById(R.id.txt_main2_totalprice);

        buyCarDBHelper = new BuyCarDBHelper(getApplicationContext());
        buyCardbrw = buyCarDBHelper.getWritableDatabase();

        myItems = new ArrayList<>();

        BuyCarListLayoutManager = new LinearLayoutManager(this);
        main2_recycler.setLayoutManager(BuyCarListLayoutManager);
        BuyCarListAdapter = new BuyCarListAdapter(MainActivity2.this,myItems);
        main2_recycler.setAdapter(BuyCarListAdapter);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_keepGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Update_Price");
        registerReceiver(receiver,intentFilter);

        showAll();
        updateTotlaPrice();
    }

    private void updateTotlaPrice(){
        total_price = total_price + 35;
        txt_main2_totalprice.setText("總計 : $ "+total_price);
    }

    private void showAll(){

        String[] colum = {"imgid","name","note","amount","price"};
        Cursor c;
        c = buyCardbrw.query("buyCarTable",colum,null,null,null,null,null);
        Log.d(TAG, "onClick: c.getCount() = "+c.getCount());
        if (c.getCount() > 0){
            c.moveToFirst();
            for (int i=0 ; i<c.getCount() ; i++){
                imgItem = c.getInt(0);
                name = c.getString(1);
                note = c.getString(2);
                amount = c.getInt(3);
                price = c.getInt(4);
                myItems.add(new BuyCarItem(imgItem,name, note,amount,price));
                total_price += amount * price;
                Log.d(TAG, "onClick: item = "+imgItem);
                Log.d(TAG, "onClick: name = "+name);
                Log.d(TAG, "onClick: note = "+note);
                Log.d(TAG, "onClick: amount = "+amount);
                Log.d(TAG, "onClick: price = "+price);
                Log.d(TAG, "onClick: -------------------");
                c.moveToNext();
            }
        }
        BuyCarListAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Update_Price")){
                Log.d(TAG, "onReceive: Update_Price");
                total_price = 0;
                myItems.clear();
                showAll();
                updateTotlaPrice();
            }
        }
    };
}