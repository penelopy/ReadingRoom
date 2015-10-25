package com.example.penelope.readingroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SelectBookListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectbooklist);
//        changeScreen();

        TextView welcome = (TextView) findViewById(R.id.welcome_message);
        welcome.setText("Welcome " + Preferences.getUsername(this));

        Button signout = (Button) findViewById(R.id.signout_button);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.removeUsername(SelectBookListActivity.this);
                Intent intent = new Intent(SelectBookListActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(SelectBookListActivity.this, "Sign out completed", Toast.LENGTH_SHORT).show();
            }

        });

        Button topPictureBooks = (Button) findViewById(R.id.get_top_picture_books_button);
        topPictureBooks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBookListActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });

        Button topChildrensBooks = (Button) findViewById(R.id.get_top_childrens_books_button);
        topChildrensBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(SelectBookListActivity.this, ChBookListActivity.class);
                startActivity(intent);
            }
        });
    }


    //delete after development complete
    public void changeScreen() {
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                // change screens
                Intent intent = new Intent(SelectBookListActivity.this, BookListActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
