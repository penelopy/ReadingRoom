package com.example.penelope.readingroom;

import android.util.Log;

import com.example.penelope.readingroom.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NYTimesAPIClient {
    public List<String> apiUrlList = new ArrayList<String>();
    private static final String TAG = "NYTimesAPIClient";

    //TODO check Children's Book url
    public NYTimesAPIClient() {
        apiUrlList.add("http://api.nytimes.com/svc/books/v3/lists/Picture-Books?api-key=8c0bbaab2101697d4147648c25ca2158:16:73271783");
        apiUrlList.add("http://api.nytimes.com/svc/books/v3/lists/chapter-books?api-key=8c0bbaab2101697d4147648c25ca2158:16:73271783");
        apiUrlList.add("http://api.nytimes.com/svc/books/v3/lists/young-adult?api-key=8c0bbaab2101697d4147648c25ca2158:16:73271783");
        apiUrlList.add("http://api.nytimes.com/svc/books/v3/lists/childrens-books?api-key=8c0bbaab2101697d4147648c25ca2158:16:73271783");
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<Book>();
        for (String apiUrl : apiUrlList) {
            List<Book> nextBatch = getData(apiUrl);
            if (nextBatch != null) {
                allBooks.addAll(nextBatch);
            }
        }
        return allBooks;
    }

    private List<Book> getData(String apiUrl) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection;
        BufferedReader reader;

        // Will contain the raw JSON response as a string.
        String jsonStr;

        try {
            //for (String apiUrl: apiUrlList) {
            URL url = new URL(apiUrl);

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
            Log.d(TAG, jsonResults.getString("list_name"));

            List<Book> result = new ArrayList<Book>();

            for (int i = 0; i < jsonBooks.length(); i++) {
                result.add(new Book(jsonBooks.getJSONObject(i), jsonResults.getString("list_name")));
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
}
