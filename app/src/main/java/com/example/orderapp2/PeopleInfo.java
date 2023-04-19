package com.example.orderapp2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PeopleInfo extends AppCompatActivity {

    private Button btn_people_ok;
    private TextView txt_peopleinfo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_info);

        btn_people_ok = (Button) findViewById(R.id.btn_people_ok);
        txt_peopleinfo_id = (TextView) findViewById(R.id.txt_peopleinfo_id);
        btn_people_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref=getSharedPreferences("price",MODE_PRIVATE);
        String ID = pref.getString("ID", "");
        // PassWord = pref.getString("PASSWORD", "");
        txt_peopleinfo_id.setText(ID);
        //edit_password.setText(PassWord);
    }
}