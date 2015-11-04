package com.example.penelope.readingroom.model;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Book {

    public static final String TITLE_KEY = "title";
    public static final String DESCRIPTION_KEY = "description";
    public static final String RANK_KEY = "rank";
    public static final String IMAGE_URL_KEY = "imageUrl";
    public static final String AUTHOR_KEY = "author";
    public static final String ISBN_KEY = "isbn";
    public static final String CATEGORY_KEY = "category";
    public static final String CURRENT_INDEX_KEY = "index";
    public static final String BOOK_COUNT = "bookCount";
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

    public static void addToIntent(Intent intent, List<Book> books, int currentIndex) {
        int index = 0;
        intent.putExtra(CURRENT_INDEX_KEY, currentIndex);
        intent.putExtra(BOOK_COUNT, books.size());
        for (Book book: books) {
            intent.putExtra(TITLE_KEY + index, book.title);
            intent.putExtra(DESCRIPTION_KEY + index, book.description);
            intent.putExtra(RANK_KEY + index, book.rank);
            intent.putExtra(IMAGE_URL_KEY + index, book.imageUrl);
            intent.putExtra(AUTHOR_KEY + index, book.author);
            intent.putExtra(ISBN_KEY + index, book.isbn);
            intent.putExtra(CATEGORY_KEY + index, book.category);
            index++;
        }
    }

    public static List<Book> readFromIntent(Intent intent) {
        List<Book> books = new ArrayList<>();
        int count = intent.getIntExtra(BOOK_COUNT, 0);

        for (int i = 0; i < count; i++) {
            books.add(new Book(intent.getStringExtra(TITLE_KEY + i),
                    intent.getStringExtra(DESCRIPTION_KEY + i),
                    intent.getStringExtra(IMAGE_URL_KEY + i),
                    intent.getStringExtra(RANK_KEY + i),
                    intent.getStringExtra(AUTHOR_KEY + i),
                    intent.getStringExtra(ISBN_KEY + i),
                    intent.getStringExtra(CATEGORY_KEY + i)));
        }
        return books;
    }

    public static int readCurrentIndex(Intent intent) {
        return intent.getIntExtra(CURRENT_INDEX_KEY, 0);
    }

}
