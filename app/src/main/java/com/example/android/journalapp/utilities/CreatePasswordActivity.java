package com.example.android.journalapp.utilities;

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

public class CreatePasswordActivity extends AppCompatActivity {

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        final EditText editTex1 = findViewById(R.id.text_pass1);
        final EditText editTex2 = findViewById(R.id.text_pass2);
        Button btn= findViewById(R.id.save_pass_btn);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String t1=editTex1.getText().toString();
        String t2=editTex2.getText().toString();
        if(t1.equals("")|| t2.equals("")){
            // there is no password enter.
            Toast.makeText(CreatePasswordActivity.this,"No Password Entered",Toast.LENGTH_SHORT).show();
            }else
                if (t1.equals(t2)){
            //saving Password
                    SharedPreferences settings=getSharedPreferences("PREFS",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putString("password",t1);
                    editor.apply();
            //Enter Application Journal Diary
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else
                {
                    Toast.makeText(CreatePasswordActivity.this,"Passwords does't Match"
                    ,Toast.LENGTH_SHORT)
                    .show();

                }

    }
});
}
}
