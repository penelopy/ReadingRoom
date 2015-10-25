package com.example.penelope.readingroom;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.penelope.readingroom.model.Book;
import com.squareup.picasso.Picasso;


public class BookDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Book book = Book.readFromIntent(getIntent());

        setContentView(R.layout.activity_detail);

        TextView bookTitle = (TextView) findViewById(R.id.bookTitle);
        bookTitle.setText(book.getTitle());

//        TextView rank = (TextView) findViewById(R.id.rank);
//        rank.setText(book.getRank());

        ImageView icon = (ImageView) findViewById(R.id.bookImage);

        Picasso.with(BookDetailActivity.this).load(book.getImageUrl()).fit().into(icon);

        TextView bookDescription = (TextView) findViewById(R.id.bookDescription);
        bookDescription.setText(book.getDescription());
    }

}