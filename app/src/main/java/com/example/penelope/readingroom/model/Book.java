package com.example.penelope.readingroom.model;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class Book {

    public static final String TITLE_KEY = "title";
    public static final String DESCRIPTION_KEY = "description";
    public static final String RANK_KEY = "rank";
    public static final String IMAGE_URL_KEY = "imageUrl";
    public static final String AUTHOR_KEY = "author";
    public static final String ISBN_KEY = "isbn";
    public static final String CATEGORY_KEY = "category";
    public String title;
    private final String description;
    private final String rank;
    private final String imageUrl;
    private final String author;
    private final String isbn;
    private final String category;

    public enum Category {
        PICTURE_BOOKS("Picture Books"),
        YOUNG_ADULT("Young Adult"),
        CHAPTER_BOOKS("Chapter Books"),
        CHILDRENS_BOOKS("Childrens Books");

        public final String listName;

        Category(String listName) {
            this.listName = listName;
        }

//        public static Category fromListName(String listName) {
//            for (Category c : values()) {
//                if (c.listName.equals(listName)) {
//                    return c;
//                }
//            }
//            throw new IllegalArgumentException("No such listName: " + listName);
//        }
    }

    public Book(JSONObject data, String category) throws JSONException {
        this(data.getString("title"), data.getString("description"), data.getString("book_image"),
                data.getString("rank"), data.getString("author"), data.getString("primary_isbn10"),
                category);
    }

    public Book(String title, String description, String imageUrl, String rank, String author,
                String isbn, String category) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rank = rank;
        this.isbn = isbn;
        this.author = author;
        this.category = category;
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

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return isbn;
    }

    public String getCategory() {
        return category;
    }

//    public String getImageUrl() {
//        if (imageUrl == null) {
//            return "https://drawception.com/pub/panels/2012/3-30/gLMH84DyGR-6.png";
//        }
//        return imageUrl;
//    }

    public static void addToIntent(Intent intent, Book book) {
        intent.putExtra(TITLE_KEY, book.title);
        intent.putExtra(DESCRIPTION_KEY, book.description);
        intent.putExtra(RANK_KEY, book.rank);
        intent.putExtra(IMAGE_URL_KEY, book.imageUrl);
        intent.putExtra(AUTHOR_KEY, book.author);
        intent.putExtra(ISBN_KEY, book.isbn);
        intent.putExtra(CATEGORY_KEY, book.category);
    }

    public static Book readFromIntent(Intent intent) {
        if (!intent.hasExtra(TITLE_KEY)) {
            throw new IllegalStateException("Intent does not include book.");
        }

        return new Book(intent.getStringExtra(TITLE_KEY),
                intent.getStringExtra(DESCRIPTION_KEY),
                intent.getStringExtra(IMAGE_URL_KEY),
                intent.getStringExtra(RANK_KEY),
                intent.getStringExtra(AUTHOR_KEY),
                intent.getStringExtra(ISBN_KEY),
                intent.getStringExtra(CATEGORY_KEY));
    }

}
