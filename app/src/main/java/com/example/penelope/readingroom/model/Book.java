package com.example.penelope.readingroom.model;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class Book {

    static final String BOOK_KEY = "book_key";
    private final String title;
    private final String description;
    private final String rank;
    private final String imageUrl;
    private final String author;

    private final String backingJson;

    public Book(JSONObject data) throws JSONException {
       this(data.getString("title"), data.getString("description"), data.getString("book_image"), data.getString("rank"), data.getString("author"), data.toString());
    }

    public Book(String title, String description, String imageUrl, String rank, String author, String backingJson) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rank = rank;
        this.author = author;
        this.backingJson = backingJson;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRank() {
        return rank;
    }

    public String getAuthor() { return author; }

    public String backingJson() {
        return backingJson;
    }

    public static void addToIntent(Intent intent, Book book) {
        intent.putExtra(BOOK_KEY, book.backingJson);
    }

    public static Book readFromIntent(Intent intent) {
        if (!intent.hasExtra(BOOK_KEY)) {
            throw new IllegalStateException("Intent does not include book_key.");
        }
        String backingJson = intent.getStringExtra(BOOK_KEY);
        try {
            return new Book(new JSONObject(backingJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
