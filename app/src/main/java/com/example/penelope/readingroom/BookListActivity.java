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
        new LoadBooksTask().execute();
    }

    private class LoadBooksTask extends AsyncTask<Void, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(Void... params) {
            return BookConnector.getInstance(BookListActivity.this).getAllBooks();
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            final ListAdapter adapter = new ArrayAdapter<Book>(BookListActivity.this, R.layout.listing_listitem, R.id.bookTitle, books) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    Book book = getItem(position);

                    TextView rank = (TextView) view.findViewById(R.id.rank);
                    rank.setText(book.getRank());

                    TextView bookTitle = (TextView) view.findViewById(R.id.bookTitle);
                    bookTitle.setText(book.getTitle());

                    TextView author = (TextView) view.findViewById(R.id.bookAuthor);
                    author.setText(book.getAuthor());

                    ImageView icon = (ImageView) view.findViewById(R.id.bookImage);

                    Picasso.with(BookListActivity.this).load(book.getImageUrl()).fit().into(icon);

                    return view;
                }
            };

            ListView listings = (ListView) findViewById(R.id.listings_list);
            listings.setAdapter(adapter);
            listings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Book book = (Book) adapter.getItem(position);
                    Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
                    Book.addToIntent(intent, book);
                    startActivity(intent);
                }
            });
        }
    }
}
