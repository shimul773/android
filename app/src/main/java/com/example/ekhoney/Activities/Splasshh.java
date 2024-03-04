package com.example.ekhoney.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.ekhoney.R;

public class Splasshh extends AppCompatActivity {

    TextView splash_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splasshh);

        splash_logo = findViewById(R.id.splash_logo);
        Animation animation = AnimationUtils.loadAnimation(Splasshh.this,R.anim.logo_animation);
        splash_logo.startAnimation(animation);

        Thread thread = new Thread(){
            public void run() {
                try {
                    sleep(3000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(Splasshh.this,MainActivity.class));
                }
            }
        };
        thread.start();
    }
}