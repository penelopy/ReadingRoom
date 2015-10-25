package com.example.penelope.readingroom;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ChBookListActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);
        new LoadBooksTask().execute();

    }

    private class LoadBooksTask extends AsyncTask<Void, Void, Book[]> {

        @Override
        protected Book[] doInBackground(Void... params) {
            return getData();
        }

        @Override
        protected void onPostExecute(Book[] books) {
            final ListAdapter adapter = new ArrayAdapter<Book>(ChBookListActivity.this, R.layout.listing_listitem, R.id.bookTitle, books) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    Book book = getItem(position);

                    //TODO I want to add a single header - this puts text on each listing
//                    TextView header = (TextView) view.findViewById(R.id.top_picture_books_header);

                    TextView rank = (TextView) view.findViewById(R.id.rank);
                    rank.setText(book.getRank());

                    TextView bookTitle = (TextView) view.findViewById(R.id.bookTitle);
                    bookTitle.setText(book.getTitle());

//                    TextView bookDescription = (TextView) view.findViewById(R.id.bookDescription);
//                    bookDescription.setText(book.getDescription());

                    ImageView icon = (ImageView) view.findViewById(R.id.bookImage);

                    Picasso.with(ChBookListActivity.this).load(book.getImageUrl()).fit().into(icon);

                    return view;
                }
            };

            ListView listings = (ListView) findViewById(R.id.listings_list);
            listings.setAdapter(adapter);
            listings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Book book = (Book) adapter.getItem(position);
                    Intent intent = new Intent(ChBookListActivity.this, BookDetailActivity.class);
                    Book.addToIntent(intent, book);
                    startActivity(intent);
                }
            });
        }
    }

    private Book[] getData() {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection;
        BufferedReader reader;

        // Will contain the raw JSON response as a string.
        String jsonStr;

        try {
            URL url = new URL("http://api.nytimes.com/svc/books/v3/lists/Childrens-Books?api-key=8c0bbaab2101697d4147648c25ca2158:16:73271783");

            // Create the request to NYTimes API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonStr = buffer.toString();
            JSONObject jsonResponse = new JSONObject(jsonStr);
            JSONObject jsonResults = jsonResponse.getJSONObject("results");
            JSONArray jsonBooks = jsonResults.getJSONArray("books");

            Book[] result = new Book[jsonBooks.length()];

            for (int i = 0; i < jsonBooks.length(); i++) {
                result[i] = new Book(jsonBooks.getJSONObject(i));
            }

            return result;
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.e("GetData", "Error ", e);
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            // TODO HANDLE THIS.
            return null;
        }
    }

    public void changeScreen() {
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                // change screens
                Intent intent = new Intent(ChBookListActivity.this, BookDetailActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
