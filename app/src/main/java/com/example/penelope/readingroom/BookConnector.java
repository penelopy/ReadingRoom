package com.example.penelope.readingroom;

import android.content.Context;
import android.util.Log;

import com.example.penelope.readingroom.model.Book;

import java.util.List;

public class BookConnector {
    private BookDbHelper db;
    private NYTimesAPIClient api;
    private static BookConnector instance;

    private static final String TAG = "BookConnector";

    private BookConnector(Context context) {
        Log.d(TAG, "BookConnector constructor");
        this.db = BookDbHelper.getInstance(context);
        this.api = new NYTimesAPIClient();
    }

    public static synchronized BookConnector getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (instance == null) {
            instance = new BookConnector(context.getApplicationContext());
        }
        return instance;
    }


    public List<Book> getAllBooks() {
        if (db.isCurrent()) {
            return db.getAllBooks();
        }
        else {
            List<Book> books = api.getAllBooks();
            db.save(books);
            return books;
        }
    }

    public List<Book> getBooksByCategory(Book.Category category) {
        if (db.isCurrent()) {
            Log.d(TAG, String.valueOf(category));
            return db.getBooksByCategory(category);
        }
        else {
            List<Book> books = api.getAllBooks();  //this should throw an exception/error
            db.save(books);
            return books;
        }
    }
}


