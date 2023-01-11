package com.compileC.aw;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class StartAni extends AppCompatActivity {
    LinearLayout linear1, linear2, linear3, linear4;
    int i = 1;
    Animation fadeOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start_ani );

        linear1 = findViewById(R.id.linear1);
            linear2 = findViewById(R.id.linear2);
            linear3 = findViewById(R.id.linear3);
            linear4 = findViewById(R.id.linear4);

            fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
            linear1.startAnimation(fadeOut);

            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("Value of i", String.valueOf(i));
                    if (i == 1) {
                        i++;
                        linear1.setBackgroundColor( Color.TRANSPARENT);
                        linear2.startAnimation(fadeOut);

                    } else if (i == 2) {
                        i++;
                        linear2.setBackgroundColor(Color.TRANSPARENT);
                        linear3.startAnimation(fadeOut);

                    } else if (i == 3) {
                        i++;
                        linear3.setBackgroundColor(Color.TRANSPARENT);
                        linear4.startAnimation(fadeOut);

                    } else if (i == 4) {
                        i++;
                        linear4.setBackgroundColor(Color.TRANSPARENT);
                        linear1.startAnimation(fadeOut);

                    } else {
                        startActivity(new Intent(StartAni.this, MainActivity.class));
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
    }

}
