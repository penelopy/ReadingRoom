package com.example.penelope.readingroom.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Book {

    private final String title;
    private final String description;
    private final String rank;
    private final String imageUrl;

    public Book(JSONObject data) throws JSONException {
        this.title = data.getString("title");
        this.description = data.getString("description");
        this.imageUrl = data.getString("book_image");
        this.rank = data.getString("rank");
    }

    public Book(String title, String description, String imageUrl, String rank) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rank = rank;
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
}
