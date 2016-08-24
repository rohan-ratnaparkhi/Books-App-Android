package com.talentica.bookshelf;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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
    LinearLayout bookDtlsLayout;
    String bookId;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        c = this;
        Bundle bundle = getIntent().getExtras();
        bookId = "";
        bookId = (String) bundle.get("bookId");

        sharedPref = getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);

        setAllFields();
        btnBorrow.setOnClickListener(this);
        populateBookDetails();
    }

    private void setAllFields() {
        bookDtlsLayout = (LinearLayout) findViewById(R.id.book_dtls_layout);
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

    private void populateBookDetails() {
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

    private void displayBookDetails(JSONObject response) {
        try {
            JSONObject data = response.getJSONObject("data");
            isbn10.setText(data.getString("isbn10"));
            isbn13.setText(data.getString("isbn13"));
            title.setText(data.getString("name"));
            publisher.setText(data.getJSONObject("publisher").getString("name"));
            String dtStart = "2010-10-15T09:27:37Z";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat toDisplayFormat = new SimpleDateFormat("MMM yyyy");
            try {
                Date date = format.parse(dtStart);
                publishedOn.setText(toDisplayFormat.format(date));
            } catch (ParseException e) {
                Log.d("Rohan", "Error while converting date");
            }
            JSONArray authors = data.getJSONArray("authors");
            if (authors.length() > 0) {
                author.setText(authors.getJSONObject(0).getString("name"));
            }
            JSONArray commentArr = data.getJSONArray("comments");
            if (commentArr.length() > 0) {
                comments.setText(commentArr.getJSONObject(0).getString("body"));
            }
            JSONArray tagArr = data.getJSONArray("tags");
            if (tagArr.length() > 0) {
                String[] strArr = new String[tagArr.length()];
                for (int i = 0; i < tagArr.length(); i++) {
                    strArr[i] = tagArr.getString(i);
                }
                tags.setText(TextUtils.join(", ", strArr));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_borrow_book:
                final Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.custom_dialog);
                TextView dialogTitle = (TextView) dialog.findViewById(R.id.custom_dialog_title);
                dialogTitle.setText("Confirmation!");
                TextView dialogBodyText = (TextView) dialog.findViewById(R.id.custom_dialog_body_text);
                dialogBodyText.setText("Are you sure you want to request for this book?");
                Button dialogBtnNo = (Button) dialog.findViewById(R.id.btn_borrow_no);
                Button dialogBtnYes = (Button) dialog.findViewById(R.id.btn_borrow_yes);
                dialogBtnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogBtnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmBookBorrow();
                        dialog.dismiss();
                    }
                });
                Window window = dialog.getWindow();
//                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                wlp.gravity = Gravity.BOTTOM;
                wlp.dimAmount = (float) 0.3;

//                this avoids dimming the background when dialog appears on screen
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                dialog.show();
                break;
        }
    }

    private void confirmBookBorrow() {
        try {
//            TODO - check once the API is working
            Log.d("Rohan", "id:"+bookId+" ;tok: " + sharedPref.getString(Constants.USER_TOKEN, ""));
            StringRequest req = new StringRequest(Request.Method.PUT,
                    Constants.BASE_URL + Constants.BORROW_BOOK_API,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Rohan", response.toString());
                            displayResponseSnack("Your request has been successfully sent.", "success");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Rohan", error.toString() + "---" + error.getLocalizedMessage());
                            displayResponseSnack("Your request could not be proceeded. Please try later.", "error");
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("bookId", bookId);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + sharedPref.getString(Constants.USER_TOKEN,""));
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(req);
        } catch (Exception e) {
            Log.d("Rohan", "error occurred: " + e.getMessage());
        }
    }

    private void displayResponseSnack(String msg, String type) {
        Snackbar snack = Snackbar.make(findViewById(R.id.book_dtls_layout), msg, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        if(type.equalsIgnoreCase("error")){
            view.setBackgroundColor(Color.parseColor(getString(R.string.snack_error_bg)));
        } else if (type.equalsIgnoreCase("success")){
            view.setBackgroundColor(Color.parseColor(getString(R.string.snack_success_bg)));
        }

//                            TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
//                            textView.setTextColor("#ffffff");
        view.setLayoutParams(params);
        snack.show();
    }
}
