package com.example.penelope.readingroom;

import android.app.Activity;
import android.os.Bundle;

//RelativeLayout mRelativeLayout;

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mRelativeLayout = new RelativeLayout(this);
//
//        ImageView i = new ImageView(this);
//        i.setImageResource(R.drawable.ferris_movie_poster);

//        mRelativeLayout.addView(i);
//        setContentView(mRelativeLayout);
        setContentView(R.layout.activity_detail);
    }
}