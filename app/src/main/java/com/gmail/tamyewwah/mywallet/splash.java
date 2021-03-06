package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv=findViewById(R.id.tv) ;
        iv=findViewById(R.id.iv);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.logo);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this,Login.class);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(splash.this, Login.class));
                finish();
            }
        }, 3000);

    }
}

