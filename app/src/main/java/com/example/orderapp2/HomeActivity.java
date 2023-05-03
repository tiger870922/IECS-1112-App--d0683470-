package com.example.orderapp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private String TAG = "HomeActivity";
    private RecyclerView detail_recycler;
    private LinearLayoutManager detailListLayoutManager;
    private DetailListAdapter detailListAdapter;
    private TextView txt_result;
    private EditText edtName;
    private Button btnSearch,btnSearcAll;

    private List<MyItem> myItems;
    MyDBHelper dbHelper;
    SQLiteDatabase dbrw;
    int item ;
    String name;
    double price;


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
        edtName = (EditText)findViewById(R.id.edtName);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearcAll = (Button) findViewById(R.id.btnSearcAll);

        btnSearch.setOnClickListener(onClick);
        btnSearcAll.setOnClickListener(onClick);

        dbHelper = new MyDBHelper(getApplicationContext());
        dbrw = dbHelper.getWritableDatabase();

        myItems = new ArrayList<>();

       /* for (int i = 0; i < 13 ; i++) {
            myItems.add(new MyItem(itemImg[i],itemName[i], itemPrice[i]));
        }*/

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
    protected void onStart() {
        super.onStart();
        showAll();
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
            case R.id.action_modify:
                Intent modify_intent = new Intent();
                modify_intent.setClass(HomeActivity.this,ModifyItemActivity.class);
                startActivity(modify_intent);
                break;
            case R.id.action_default:
                ResetItem();
                break;
            case R.id.action_finish:
                Log.d(TAG, "onOptionsItemSelected: action_finish");
                finish();
                //return true;
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnSearch:
                    Log.d(TAG, "onClick: btnSearch");

                    //dbHelper = new MyDBHelper(getApplicationContext());
                    //dbrw = dbHelper.getWritableDatabase();

                    /*for (int i = 0; i < 13 ; i++) {
                        item = itemImg[i];
                        name = itemName[i];
                        price = itemPrice[i];
                        ContentValues cv = new ContentValues();
                        cv.put("imgid",item);
                        cv.put("name",name);
                        cv.put("price",price);
                        dbrw.insert("myTable",null,cv);
                    }*/

                    //dbrw.delete("myTable",null,null);
                    //edit_name.setText("");

                    Search();
                    break;
                case R.id.btnSearcAll:
                    Log.d(TAG, "onClick: btnSearcAll");
                    showAll();
                    break;
            }
        }
    };

    private void Search(){
        String[] colum = {"imgid","name","price"};
        Cursor c;

        if (edtName.getText().toString().equals("")){
            c = dbrw.query("myTable",colum,null,null,null,null,null);
        }else {
            c = dbrw.query("myTable",colum,"name="+"'"+edtName.getText().toString()+"'",
                    null,null,null,null);
        }
        myItems.clear();
        Log.d(TAG, "onClick: c.getCount() = "+c.getCount());
        if (c.getCount() > 0){
            c.moveToFirst();
            for (int i=0 ; i<c.getCount() ; i++){
                item = c.getInt(0);
                name = c.getString(1);
                price = c.getInt(2);
                myItems.add(new MyItem(item,name, (float) price));
                c.moveToNext();
                Log.d(TAG, "onClick: item = "+item);
                Log.d(TAG, "onClick: name = "+name);
                Log.d(TAG, "onClick: price = "+price);
                Log.d(TAG, "onClick: -------------------");

            }
        }
        detailListAdapter.notifyDataSetChanged();
    }

    private void showAll(){
        String[] colum = {"imgid","name","price"};
        Cursor c;
        myItems.clear();
        /*if (edtName.getText().toString().equals("")){
            c = dbrw.query("myTable",colum,null,null,null,null,null);
        }else {
            c = dbrw.query("myTable",colum,"name="+"'"+edtName.getText().toString()+"'",
                    null,null,null,null);
        }*/
        c = dbrw.query("myTable",colum,null,null,null,null,null);
        Log.d(TAG, "onClick: c.getCount() = "+c.getCount());
        if (c.getCount() > 0){
            c.moveToFirst();
            for (int i=0 ; i<c.getCount() ; i++){
                item = c.getInt(0);
                name = c.getString(1);
                price = c.getInt(2);
                myItems.add(new MyItem(item,name, (float) price));
                c.moveToNext();
                Log.d(TAG, "onClick: item = "+item);
                Log.d(TAG, "onClick: name = "+name);
                Log.d(TAG, "onClick: price = "+price);
                Log.d(TAG, "onClick: -------------------");

            }
        }
        detailListAdapter.notifyDataSetChanged();
        edtName.setText("");
    }

    private void ResetItem(){
        dbrw.delete("myTable",null,null);
        for (int i = 0; i < 13 ; i++) {
            item = itemImg[i];
            name = itemName[i];
            price = itemPrice[i];
            ContentValues cv = new ContentValues();
            cv.put("imgid",item);
            cv.put("name",name);
            cv.put("price",price);
            dbrw.insert("myTable",null,cv);
        }
        showAll();
    }
}