package com.example.android.journalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.journalapp.utilities.ConnectivityReceiver;
import com.example.android.journalapp.utilities.CreatePasswordActivity;
import com.example.android.journalapp.utilities.EntryPasswordActivity;

public class SplashActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {
String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //load password from cash.
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password=settings.getString("password","");

        //Thread for sleeping and finish activity splash and wake up Sign Activity
        Thread thread=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3000);
                    checkConnection();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

     private void checkConnection() {
         boolean isConnected = ConnectivityReceiver.isConnected();
         if(isConnected){
             Intent i = new Intent(SplashActivity.this, SignInActivity.class);
             startActivity(i);
             }else {
                if (password.equals("")){
                    //there is no password saved.
                    Intent i = new Intent(SplashActivity.this, CreatePasswordActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(SplashActivity.this, EntryPasswordActivity.class);
                    startActivity(i);
                }
             }
         }
     @Override
     protected void onResume() {
         super.onResume();

         // register connection status listener
         MyApplication.getInstance().setConnectivityListener(this);
     }
 @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
