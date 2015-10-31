package com.example.penelope.readingroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.penelope.readingroom.model.Book;


public class SelectBookListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectbooklist);

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
                intent.putExtra("category", Book.Category.PICTURE_BOOKS.ordinal());

                startActivity(intent);
            }
        });

        Button topChapterBooks = (Button) findViewById(R.id.get_top_chapter_books_button);
        topChapterBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBookListActivity.this, BookListActivity.class);
                intent.putExtra("category", Book.Category.CHAPTER_BOOKS.ordinal());
                startActivity(intent);
            }
        });

        Button topYoungAdultBooks = (Button) findViewById(R.id.get_young_adult_books_button);
        topYoungAdultBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBookListActivity.this, BookListActivity.class);
                intent.putExtra("category", Book.Category.YOUNG_ADULT.ordinal());
                startActivity(intent);
            }
        });
    }
}
