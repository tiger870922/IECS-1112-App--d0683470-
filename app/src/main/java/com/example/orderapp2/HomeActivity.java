package com.example.orderapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private String TAG = "HomeActivity";
    private RecyclerView detail_recycler;
    private LinearLayoutManager detailListLayoutManager;
    DetailListAdapter detailListAdapter;
    private TextView txt_result;

    private int[] itemImg = {R.drawable.hambuger1,R.drawable.hambuger2,R.drawable.hambuger3,
            R.drawable.fried1,R.drawable.fried2,R.drawable.fried3,R.drawable.fried4,
            R.drawable.black,R.drawable.green,R.drawable.cola,R.drawable.lemontea,
            R.drawable.sprite,R.drawable.milktea};

    private String[] itemName = {"雙層麥香雞堡","大麥克","雙層牛肉吉士堡",
            "甜辣韓風炸雞","麥脆雞","麥克雞塊","薯條",
            "紅茶","綠茶","可樂","檸檬紅茶","雪碧","奶茶"};
    private float[] itemPrice = {50,70,50,40,45,50,30,25,25,25,30,30,30,30};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView(){
        detail_recycler = (RecyclerView) findViewById(R.id.detail_recycler);
        txt_result = (TextView)findViewById(R.id.txt_result);

        List<MyItem> myItems = new ArrayList<>();

        for (int i = 0; i < 13 ; i++) {
            myItems.add(new MyItem(itemImg[i],itemName[i], itemPrice[i]));
        }

        detailListLayoutManager = new LinearLayoutManager(this);
        detail_recycler.setLayoutManager(detailListLayoutManager);
        detailListAdapter = new DetailListAdapter(HomeActivity.this,myItems);
        detail_recycler.setAdapter(detailListAdapter);

        txt_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: txt_result");
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                Log.d(TAG, "onOptionsItemSelected: action_about");
                //txt_optionMenu.setText("已點選功能表選單");
                //Toast.makeText(MainActivity.this,"action_about",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this,PeopleInfo.class);
                startActivity(intent);
                //return true;
                break;
            case R.id.action_finish:
                Log.d(TAG, "onOptionsItemSelected: action_finish");
                finish();
                //return true;
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}