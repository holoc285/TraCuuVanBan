package com.holoc284_v001.tracuuvanban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.login.view.LoginActivity;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{

    RelativeLayout layout;
    LinearLayout logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        anhXa();

        Animation trasition = AnimationUtils.loadAnimation(this, R.anim.trasition_icon);
        logo.setAnimation(trasition);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_backgroup);
        layout.setAnimation(animation);
        animation.setAnimationListener(this);
    }
    private void anhXa(){
        layout = (RelativeLayout) findViewById(R.id.layout);
        logo = (LinearLayout) findViewById(R.id.logo);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}
