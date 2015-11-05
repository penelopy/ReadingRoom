package com.example.penelope.readingroom;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.penelope.readingroom.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);



        int value = getIntent().getIntExtra("category", Book.Category.PICTURE_BOOKS.ordinal());
        Book.Category category = Book.Category.values()[value];

        new LoadBooksTask().execute(category);
    }

    private class LoadBooksTask extends AsyncTask<Book.Category, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(Book.Category... params) {
//            return BookConnector.getInstance(BookListActivity.this).getAllBooks();

            return BookConnector.getInstance(BookListActivity.this).getBooksByCategory(params[0]);
        }

        @Override
        protected void onPostExecute(final List<Book> books) {
            final ListAdapter adapter = new ArrayAdapter<Book>(BookListActivity.this,
                    R.layout.listing_listitem, R.id.bookTitle, books) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {


                    View view = super.getView(position, convertView, parent);

                    Book book = getItem(position);

                    TextView rank = (TextView) view.findViewById(R.id.rank);
                    TextView bookTitle = (TextView) view.findViewById(R.id.bookTitle);
                    ImageView icon = (ImageView) view.findViewById(R.id.bookImage);

                    rank.setText(book.getRank());
                    bookTitle.setText(book.getTitle());

                    Picasso.with(BookListActivity.this).load(book.getImageUrl()).fit().into(icon);

                    return view;
                }
            };

            ListView listings = (ListView) findViewById(R.id.listings_list);
            listings.setAdapter(adapter);
            listings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
                    Book.addToIntent(intent, books, position);

                    startActivity(intent);
                }
            });
        }
    }
}
