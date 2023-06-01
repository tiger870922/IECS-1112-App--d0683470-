package com.example.orderapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SelectImageActivity extends AppCompatActivity {

    private String TAG = "SelectImageActivity";

    private ListView lv_selectImg;
    private List<SelectItem> myItems;
    private PictureListAdapter adapter;

    private int[] selectitemImg = {R.drawable.item1,R.drawable.item2,R.drawable.item3,R.drawable.item4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_select_image);


        lv_selectImg = (ListView)findViewById(R.id.lv_selectImg);

        myItems = new ArrayList<>();

        for (int i=0; i<4 ;i++){
            myItems.add(new SelectItem(selectitemImg[i]));
        }

        adapter = new PictureListAdapter(getApplicationContext(),myItems);
        lv_selectImg.setAdapter(adapter);
        lv_selectImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: i = "+i);
                Intent intent_Broadcast = new Intent();
                intent_Broadcast.setAction("Set_picture");
                Bundle bundle = new Bundle();
                bundle.putInt("position",i);
                intent_Broadcast.putExtra("msg1",bundle);
                sendBroadcast(intent_Broadcast);
                finish();
            }
        });

    }
}