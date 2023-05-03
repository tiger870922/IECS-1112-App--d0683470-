package com.example.orderapp2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);
        initView();
    }

    private void initView(){
        btnAppend = (Button) findViewById(R.id.btnAppend);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClear = (Button) findViewById(R.id.btnClear);
        edit_modify_name = (EditText) findViewById(R.id.edit_modify_name);
        edit_modify_price = (EditText) findViewById(R.id.edit_modify_price);
        modify_recycler = (RecyclerView) findViewById(R.id.modify_recycler);

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
            }

        }
    };

    private void NewItem(){
        if (edit_modify_name.getText().toString().equals("") ||
                edit_modify_price.getText().toString().equals("")){
            Toast.makeText(this,"輸入不完全",Toast.LENGTH_SHORT).show();
        }else {
            item = R.drawable.itemfood;
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
        String[] colum = {"imgid","name","price"};
        Cursor c;
        myItems.clear();
        if (edit_modify_name.getText().toString().equals("")){
            c = dbrw.query("myTable",colum,null,null,null,null,null);
        }else {
            c = dbrw.query("myTable",colum,"name="+"'"+edit_modify_name.getText().toString()+"'",
                    null,null,null,null);
        }
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
}