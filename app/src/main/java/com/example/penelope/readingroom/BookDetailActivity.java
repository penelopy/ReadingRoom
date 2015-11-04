package com.example.penelope.readingroom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.penelope.readingroom.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookDetailActivity extends Activity {

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookDescription;
    private TextView rank;
    private ImageView icon;
    private Button nextButton;
    private Button prevButton;
    private List<Book> books;
    private int index;

    private void updateEnabledStates() {
        prevButton.setEnabled(index > 0);
        nextButton.setEnabled(index < books.size() - 1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        books = Book.readFromIntent(getIntent());
        index = Book.readCurrentIndex(getIntent());

        setContentView(R.layout.activity_detail);

        bookAuthor = (TextView) findViewById(R.id.bookAuthor);
        bookTitle = (TextView) findViewById(R.id.bookTitle);
        bookDescription = (TextView) findViewById(R.id.bookDescription);
        rank = (TextView) findViewById(R.id.rank);
        icon = (ImageView) findViewById(R.id.bookImage);
        nextButton = (Button) findViewById(R.id.nextButton);
        prevButton = (Button) findViewById(R.id.prevButton);




        setBook(books.get(index));

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //add check
                index--;
                setBook(books.get(index));
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                setBook(books.get(index));
            }
        });


    }

    private void setBook(Book book) {
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookDescription.setText(book.getDescription());
        rank.setText(book.getRank());

        Picasso.with(BookDetailActivity.this).load(book.getImageUrl()).fit().into(icon);

        YoYo.with(Techniques.Flash)
                .duration(700)
                .playOn(findViewById(R.id.bookTitle));
        updateEnabledStates();
    }


}