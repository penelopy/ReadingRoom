package com.example.penelope.readingroom;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeScreen();

        ImageView icon = (ImageView)findViewById(R.id.preview);
        Picasso.with(MainActivity.this).load("http://moda-fashion.biz/wp-content/uploads/2015/01/wpid-White-Radial-Gradient-Background-2015-2016-0.jpg").fit().into(icon);



    }

    public void changeScreen() {
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}

