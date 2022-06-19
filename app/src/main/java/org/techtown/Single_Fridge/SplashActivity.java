//SplashActivity.java

package org.techtown.Single_Fridge;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.Single_Fridge.R;

public class SplashActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.imageView_name_2);
        LinearLayout fade_ing =findViewById(R.id.fadein_out);
        Animation anim_FadeIn = AnimationUtils.loadAnimation(this,R.anim.anim_splash_fadein);
        Animation anim_splash_bounce = AnimationUtils.loadAnimation(this,R.anim.anim_splash_bounce);

        fade_ing.startAnimation(anim_FadeIn);

        anim_FadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(anim_splash_bounce);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim_splash_bounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


/*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

 */
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}