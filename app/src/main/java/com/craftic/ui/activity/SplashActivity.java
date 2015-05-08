package com.craftic.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.craftic.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by keren on 4/28/15.
 */

public class SplashActivity extends Activity {
    //duration of splash screen
    long Delay = 5000;
    private ImageView animatedLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

//get view from activity_splash.xml
        setContentView(R.layout.activity_splash);

        animatedLogo = (ImageView)findViewById(R.id.animatedLogo);
        YoYo.with(Techniques.BounceInDown)
                .delay(500)
                .duration(1000)
                .playOn(animatedLogo);




//create timer
        Timer RunSplash = new Timer();

//when timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
//close splashscreen activity
                finish();

//start main activity
                Intent myIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(myIntent);



            }
        };
        RunSplash.schedule(ShowSplash, Delay);
    }
}

