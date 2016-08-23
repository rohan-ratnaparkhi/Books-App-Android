package com.talentica.bookshelf;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookDetailsActivity extends AppCompatActivity {

    ImageView img;
    TextView title;
    TextView author;
    TextView lender;
    TextView binding;
    TextView condition;
    TextView genre;
    TextView publishedOn;
    TextView publisher;
    TextView isbn10;
    TextView isbn13;
    TextView edition;
    TextView listPrice;
    TextView tags;
    TextView comments;
    Button btnBorrow;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Bundle bundle = getIntent().getExtras();
        String bookId = (String) bundle.get("bookId");

        sharedPref = getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);

        setAllFields();

        populateBookDetails(bookId);
    }

    private void setAllFields() {
        img = (ImageView) findViewById(R.id.img_book_details);
        title = (TextView) findViewById(R.id.tv_book_details_title);
        author = (TextView) findViewById(R.id.tv_book_details_author);
        lender = (TextView) findViewById(R.id.tv_book_details_lender);
        binding = (TextView) findViewById(R.id.tv_book_details_binding);
        condition = (TextView) findViewById(R.id.tv_book_details_condition);
        genre = (TextView) findViewById(R.id.tv_book_details_genre);
        publishedOn = (TextView) findViewById(R.id.tv_book_details_published);
        publisher = (TextView) findViewById(R.id.tv_book_details_publisher);
        isbn13 = (TextView) findViewById(R.id.tv_book_details_isbn13);
        isbn10 = (TextView) findViewById(R.id.tv_book_details_isbn10);
        edition = (TextView) findViewById(R.id.tv_book_details_edition);
        listPrice = (TextView) findViewById(R.id.tv_book_details_list_price);
        tags = (TextView) findViewById(R.id.tv_book_details_tags);
        comments = (TextView) findViewById(R.id.tv_book_details_owner_comment);
        btnBorrow = (Button) findViewById(R.id.btn_borrow_book);
    }

    private void populateBookDetails(String bookId) {
        try {

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                    Constants.BASE_URL + Constants.GET_BOOK_BY_ID.replace("{id}", bookId),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Rohan", response.toString());
                            displayBookDetails(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Rohan", error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + sharedPref.getString(Constants.USER_TOKEN, ""));
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(req);

        } catch (Exception e) {
            Log.d("Rohan", "error: " + e.getMessage());
        }

    }

    private void displayBookDetails(JSONObject response){
        try {
            JSONObject data = response.getJSONObject("data");
            isbn10.setText(data.getString("isbn10"));
            isbn13.setText(data.getString("isbn13"));
            title.setText(data.getString("name"));
            publisher.setText(data.getJSONObject("publisher").getString("name"));
//            String dtStart = "2010-10-15T09:27:37Z";
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//            try {
//                Date date = format.parse(dtStart);
//                date.getTime();
//
//                Log.d("Rohan", date.toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            publishedOn.setText();
            JSONArray authors = data.getJSONArray("authors");
            if(authors.length() > 0){
                author.setText(authors.getJSONObject(0).getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
