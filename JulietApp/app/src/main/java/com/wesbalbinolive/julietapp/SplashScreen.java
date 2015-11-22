package com.wesbalbinolive.julietapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {

    private ImageView splash_logo;
    private Animation animation;
    private ProgressBar pg_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash_logo = (ImageView) findViewById(R.id.splash_logo);
        pg_splash = (ProgressBar) findViewById(R.id.pg_splash);
        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.splash_animation);

        splash_logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pg_splash.setVisibility(View.GONE);
                finish();
                Intent intent = new Intent(SplashScreen.this, TermosActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
