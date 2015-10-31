package com.example.penelope.readingroom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.penelope.readingroom.model.Book;

import java.util.ArrayList;
import java.util.List;


public class BookDbHelper extends SQLiteOpenHelper {
    // Database info
    public static final String DATABASE_NAME = "Books.db";
    private static final int DATABASE_VERSION = 3;

    // Using singleton pattern
    private static BookDbHelper instance;

    private static final String KEY_BOOK_TITLE = "title";

    private static final String TAG = "BookDbHelper";


    // Constructor should be private to prevent direct instantiation. Make a call
    // to static method "getInstance()" instead.
    private BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "BookDbHelper constructor");
    }

    public static synchronized BookDbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (instance == null) {
            instance = new BookDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        Log.d(TAG, "onConfigure()");
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade()");
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onDowngrade()");
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean isCurrent() {
        Log.d(TAG, "isCurrent()");
        // TODO ideally this checks a timestamp and returns true if the data is less than
        // 24 hours stale, or something comparable
        List<Book> books = getAllBooks();
        Log.d(TAG, "isCurrent(), books.size() = " + books.size());

        return !books.isEmpty();
    }


    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String COLUMN_NAME_ISBN = "isbn";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_RANK = "rank";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }

    private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ISBN + " TEXT, " +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    FeedEntry.COLUMN_NAME_AUTHOR + " TEXT, " +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    FeedEntry.COLUMN_NAME_IMAGE_URL + " TEXT, " +
                    FeedEntry.COLUMN_NAME_RANK + " TEXT, " +
                    FeedEntry.COLUMN_NAME_CATEGORY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public void save(List<Book> books) {
        Log.d(TAG, "save()");
        //Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with
        // performance and ensures consistency of the database.
        db.beginTransaction();
        db.delete(FeedEntry.TABLE_NAME, null, null);
//        db.setTransactionSuccessful();
//        db.endTransaction();
//
//        db.beginTransaction();

        try {
            for (Book book : books) {
                ContentValues values = new ContentValues();
                values.put(FeedEntry.COLUMN_NAME_ISBN, book.getISBN());
                values.put(FeedEntry.COLUMN_NAME_AUTHOR, book.getAuthor());
                values.put(FeedEntry.COLUMN_NAME_TITLE, book.getTitle());
                values.put(FeedEntry.COLUMN_NAME_DESCRIPTION, book.getDescription());
                if (book.getImageUrl() == null) {
                    Log.d(TAG, book.getImageUrl());
                    values.put(FeedEntry.COLUMN_NAME_IMAGE_URL, "https://drawception.com/pub/panels/2012/3-30/gLMH84DyGR-6.png");
                }
                else {
                    values.put(FeedEntry.COLUMN_NAME_IMAGE_URL, book.getImageUrl());
                }
                values.put(FeedEntry.COLUMN_NAME_RANK, book.getRank());
                values.put(FeedEntry.COLUMN_NAME_CATEGORY, book.getCategory());
                db.insertOrThrow(FeedEntry.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
            Log.d(TAG, "Inserted " + books.size() + " books.");
            Log.d(TAG, "Table columns" + DATABASE_TABLE_CREATE);
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add books to database", e);
        } finally {
            db.endTransaction();
        }

    }

    public List<Book> getAllBooks() {
//        Log.d(TAG, "getAllBooks()");
        List<Book> books = new ArrayList<>();

        String BOOKS_SELECT_QUERY = "SELECT * FROM " + FeedEntry.TABLE_NAME;

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(BOOKS_SELECT_QUERY, null);
        try {
//            Log.d(TAG, "getAllBooks() about to moveToFirst()");
            if (cursor.moveToFirst()) {
//                Log.d(TAG, "getAllBooks() just moved to first");
                do {
                    String title = cursor.getString(cursor.getColumnIndex(KEY_BOOK_TITLE));
                    String description = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_DESCRIPTION));
                    String imageUrl = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_IMAGE_URL));
                    String rank = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_RANK));
                    String author = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_AUTHOR));
                    String isbn = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_ISBN));
                    String category = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_CATEGORY));
                    Book newBook = new Book(title, description, imageUrl, rank, author, isbn, category);

                    books.add(newBook);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to get books from database", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return books;
    }

    public List<Book> getBooksByCategory(Book.Category category) {
//        Log.d(TAG, "getBooksByCategory()");
        List<Book> books = new ArrayList<>();

        String BOOKS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = \"%s\"",
                        FeedEntry.TABLE_NAME,
                        FeedEntry.COLUMN_NAME_CATEGORY,
                        category.listName);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(BOOKS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex(KEY_BOOK_TITLE));
                    String description = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_DESCRIPTION));
                    String imageUrl = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_IMAGE_URL));
                    String rank = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_RANK));
                    String author = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_AUTHOR));
                    String isbn = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_ISBN));
                    String categoryx = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_CATEGORY));
                    Book newBook = new Book(title, description, imageUrl, rank, author, isbn, categoryx);

                    books.add(newBook);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to get books from database", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return books;
    }


}


