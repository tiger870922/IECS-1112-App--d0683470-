package com.example.orderapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class CustomizationActivity extends AppCompatActivity {

    private String TAG = "CustomizationActivity";
    private Button btn_custom_ok;
    private int id[] ={R.id.cb1,R.id.cb2,R.id.cb3,R.id.cb4,R.id.cb5};
    private CheckBox chk;
    String msg ="";
    String name;
    float price;
    int img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_customization);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        price = bundle.getFloat("Price");
        img = bundle.getInt("Image");
        Log.d(TAG, "onCreate: "+name);
        Log.d(TAG, "onCreate: "+price);
        Log.d(TAG, "onCreate: "+img);


        btn_custom_ok = (Button) findViewById(R.id.btn_custom_ok);
        btn_custom_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg ="";
                for (int i:id){
                    chk = (CheckBox) findViewById(i);
                    if (chk.isChecked()){
                        msg+=chk.getText()+"。";
                    }
                }
                Log.d(TAG, "onClick: "+msg);
                Intent intent_Broadcast = new Intent();
                intent_Broadcast.setAction("Add_Item");
                Bundle bundle=new Bundle();
                bundle.putString("Name",name);
                bundle.putString("Msg",msg);
                bundle.putFloat("Price",price);
                bundle.putInt("Image",img);
                intent_Broadcast.putExtras(bundle);
                sendBroadcast(intent_Broadcast);
                finish();
            }
        });
    }
}