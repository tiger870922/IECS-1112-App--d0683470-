package com.example.orderapp2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ModifyItemActivity extends AppCompatActivity {
    private String TAG = "ModifyItemActivity";

    private Button btnAppend,btnEdit,btnDelete,btnClear;
    private EditText edit_modify_name,edit_modify_price;

    private RecyclerView modify_recycler;
    private LinearLayoutManager detailListLayoutManager;
    DetailListAdapter detailListAdapter;
    private List<MyItem> myItems;
    MyDBHelper dbHelper;
    SQLiteDatabase dbrw;
    int item ;
    String name;
    double price;
    private CardView cv_modify_img;
    private ImageView cv_item_img;
    private Bitmap bitmap;
    private int msg_position = 4;
    private int[] itemImg = {R.drawable.item1,R.drawable.item2,R.drawable.item3,R.drawable.item4,R.drawable.itemfood};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_modify_item);
        initView();
    }

    private void initView(){
        SaveItemImg();
        msg_position = 4;
        btnAppend = (Button) findViewById(R.id.btnAppend);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClear = (Button) findViewById(R.id.btnClear);
        edit_modify_name = (EditText) findViewById(R.id.edit_modify_name);
        edit_modify_price = (EditText) findViewById(R.id.edit_modify_price);
        modify_recycler = (RecyclerView) findViewById(R.id.modify_recycler);
        cv_modify_img = (CardView)findViewById(R.id.cv_modify_img);
        cv_item_img = (ImageView)findViewById(R.id.cv_item_img);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Set_picture");
        registerReceiver(receiver,intentFilter);

        dbHelper = new MyDBHelper(getApplicationContext());
        dbrw = dbHelper.getWritableDatabase();
        myItems = new ArrayList<>();
        detailListLayoutManager = new LinearLayoutManager(this);
        modify_recycler.setLayoutManager(detailListLayoutManager);
        detailListAdapter = new DetailListAdapter(ModifyItemActivity.this,myItems);
        modify_recycler.setAdapter(detailListAdapter);

        btnAppend.setOnClickListener(onClick);
        btnEdit.setOnClickListener(onClick);
        btnDelete.setOnClickListener(onClick);
        btnClear.setOnClickListener(onClick);
        cv_modify_img.setOnClickListener(onClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAll();
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnAppend:
                    NewItem();
                    break;
                case R.id.btnEdit:
                    EditItem();
                    break;
                case R.id.btnDelete:
                    DeleteItem();
                    break;
                case R.id.btnClear:
                    ClearItem();
                    break;
                case R.id.cv_modify_img:
                    Log.d(TAG, "onClick: cv_modify_name");
                    Intent intent = new Intent();
                    intent.setClass(ModifyItemActivity.this,SelectImageActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    };

    private void SaveItemImg(){
        for (int i=0; i<4; i++){
            bitmap = BitmapFactory.decodeResource(ModifyItemActivity.this.getResources(), itemImg[i]);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput("picture"+i, Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void NewItem(){
        if (edit_modify_name.getText().toString().equals("") ||
                edit_modify_price.getText().toString().equals("")){
            Toast.makeText(this,"輸入不完全",Toast.LENGTH_SHORT).show();
        }else {
            //item = R.drawable.itemfood;
            item = itemImg[msg_position];
            name = edit_modify_name.getText().toString();
            price = Integer.parseInt(edit_modify_price.getText().toString());
            //price = Double.parseDouble(edit_modify_price.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("imgid",item);
            cv.put("name",name);
            cv.put("price",price);
            dbrw.insert("myTable",null,cv);
            edit_modify_name.setText("");
            edit_modify_price.setText("");
            cv_item_img.setImageResource(R.drawable.itemfood);
        }
        showAll();
    }

    private void EditItem(){
        if (edit_modify_name.getText().toString().equals("") ||
                edit_modify_price.getText().toString().equals("")){
            Toast.makeText(this,"輸入不完全",Toast.LENGTH_SHORT).show();
        }else {
            double newprice = Integer.parseInt(edit_modify_price.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("price",newprice);
            dbrw.update("myTable",cv,"name="+"'"+edit_modify_name.getText().toString()+"'",null);
            edit_modify_name.setText("");
            edit_modify_price.setText("");
        }
        showAll();
    }
    private void DeleteItem(){
        if (edit_modify_name.getText().toString().equals("")){
            Toast.makeText(this,"請輸入名稱",Toast.LENGTH_SHORT).show();
        }else {
            dbrw.delete("myTable","name="+"'"+edit_modify_name.getText().toString()+"'",null);
            edit_modify_name.setText("");
        }
        showAll();
    }

    private void ClearItem(){
        dbrw.delete("myTable",null,null);
        edit_modify_name.setText("");
        edit_modify_price.setText("");
        showAll();
    }

    private void showAll(){
        Log.d(TAG, "showAll: ");
        String[] colum = {"imgid","name","price"};
        Cursor c;
        myItems.clear();
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
                //Log.d(TAG, "onClick: item = "+item);
                //Log.d(TAG, "onClick: name = "+name);
                //Log.d(TAG, "onClick: price = "+price);
                //Log.d(TAG, "onClick: -------------------");

            }
        }
        detailListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Set_picture")){
                Log.d(TAG, "onReceive: Set_picture");
                Bundle bundle = intent.getBundleExtra("msg1");
                msg_position = bundle.getInt("position");
                Log.d(TAG, "onReceive: msg_position = "+msg_position);
                try {
                    bitmap = BitmapFactory.decodeStream(openFileInput("picture"+msg_position));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                cv_item_img.setImageBitmap(bitmap);
                showAll();
            }
        }
    };
}