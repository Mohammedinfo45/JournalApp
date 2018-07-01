package com.example.android.journalapp.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.journalapp.MainActivity;
import com.example.android.journalapp.R;
import com.example.android.journalapp.SplashActivity;

public class EntryPasswordActivity extends AppCompatActivity {
EditText editText;
Button btnEnter;
String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_password);
        //load password from cash.
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password=settings.getString("password","");
        //define view
        editText =findViewById(R.id.text_enter_pass1);
        btnEnter=findViewById(R.id.enter_pass_btn);
        //Button Enter Application
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String text=editText.getText().toString();
              if(text.equals(password)){
                  // enter app
                  Intent i = new Intent(getApplicationContext(), MainActivity.class);
                  startActivity(i);
                  finish();
                } else{
                  Toast.makeText(EntryPasswordActivity.this
                  ,"Wrong password",Toast.LENGTH_SHORT).show();
              }
            }
        });
    }
}
