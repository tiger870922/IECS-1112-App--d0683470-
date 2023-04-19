package com.example.orderapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private EditText edit_id,edit_password;
    private Button btn_register,btn_login;
    private TextView txt_main_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_password = (EditText) findViewById(R.id.edit_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);

        /*SharedPreferences pref=getSharedPreferences("price",MODE_PRIVATE);
        String ID = pref.getString("ID", "");
        String PassWord = pref.getString("PASSWORD", "");
        edit_id.setText(ID);
        edit_password.setText(PassWord);*/

        btn_register.setOnClickListener(onClick);
        btn_login.setOnClickListener(onClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref=getSharedPreferences("price",MODE_PRIVATE);
        String ID = pref.getString("ID", "");
        String PassWord = pref.getString("PASSWORD", "");
        edit_id.setText(ID);
        edit_password.setText(PassWord);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_register:
                    Log.d(TAG, "onClick: btn_register");
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_login:
                    Log.d(TAG, "onClick: btn_login");
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this,HomeActivity.class);
                    startActivity(intent2);
                    break;
            }

        }
    };
}