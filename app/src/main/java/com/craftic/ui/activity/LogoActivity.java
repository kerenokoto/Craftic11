package com.craftic.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.craftic.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;



/**
 * Created by keren on 5/2/15.
 */
public class LogoActivity extends Activity {
ImageView imageView;
    @Override

    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_splash);

        imageView = (ImageView) findViewById(R.id.animatedLogo);
        //final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        YoYo.with(Techniques.Pulse)
                .delay(500)
                .duration(1000)
                .playOn(imageView);




    }
}
