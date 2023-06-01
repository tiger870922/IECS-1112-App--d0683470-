package com.example.orderapp2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "RegisterActivity";
    private EditText edit_register_account,edit_register_password,edit_register_checkpassword;
    private Button btn_register_signup;
    private TextView txt_register_info;
    private EditText edit_register_id,edit_register_phone,edit_register_mail,edit_register_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        edit_register_account = (EditText) findViewById(R.id.edit_register_account);
        edit_register_password = (EditText) findViewById(R.id.edit_register_password);
        edit_register_checkpassword = (EditText) findViewById(R.id.edit_register_checkpassword);
        btn_register_signup = (Button) findViewById(R.id.btn_register_signup);
        txt_register_info = (TextView) findViewById(R.id.txt_register_info);
        edit_register_id = (EditText) findViewById(R.id.edit_register_id);
        edit_register_phone = (EditText) findViewById(R.id.edit_register_phone);
        edit_register_mail = (EditText) findViewById(R.id.edit_register_mail);
        edit_register_address = (EditText) findViewById(R.id.edit_register_address);


        btn_register_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String temp = edit_register_checkpassword.getText().toString();


                //if (edit_register_id.equals("") || edit_register_password.equals("")){
                if (edit_register_account.getText().toString().isEmpty()
                        || edit_register_password.getText().toString().isEmpty()){
                    txt_register_info.setText("請檢查帳號密碼");
                }else if(edit_register_password.getText().toString().equals(temp)){
                    SharedPreferences pref=getSharedPreferences("price",MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("ACCOUNT",edit_register_account.getText().toString());
                    editor.putString("ID",edit_register_id.getText().toString());
                    editor.putString("PASSWORD",edit_register_password.getText().toString());
                    editor.putString("PHONE",edit_register_phone.getText().toString());
                    editor.putString("MAIL",edit_register_mail.getText().toString());
                    editor.putString("ADDRESS",edit_register_address.getText().toString());
                    editor.commit();
                    editor.apply();
                    finish();
                }else {
                    txt_register_info.setText("兩次密碼輸入不同");
                }
            }
        });

    }
}