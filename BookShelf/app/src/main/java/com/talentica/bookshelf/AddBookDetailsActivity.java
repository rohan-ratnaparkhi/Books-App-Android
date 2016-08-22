package com.talentica.bookshelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddBookDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    Button cancelAction;
    Button submitAction;
    String validationMsg;

    EditText bookTitle;
    EditText bookAuthor;
    EditText isbn13;
    EditText isbn10;
    EditText bookPublisher;
    EditText bookPrice;
    Spinner binding;
    EditText tags;
    EditText bookComments;
    Spinner genre;
    RadioGroup condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_details);

        cancelAction = (Button) findViewById(R.id.btn_add_book_cancel);
        submitAction = (Button) findViewById(R.id.btn_add_book_submit);

        cancelAction.setOnClickListener(this);
        submitAction.setOnClickListener(this);

        displayAddBookDetails();
    }

    private void displayAddBookDetails() {
        BookDetailsManuallyFragment manualDtls = new BookDetailsManuallyFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.prev_or_new_details, manualDtls).commit();
        RemaininBookDetailsFragment remainingDtls = new RemaininBookDetailsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_remaining_details, remainingDtls).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_book_cancel:
                finish();
                break;
            case R.id.btn_add_book_submit:
                saveBook();
                break;
        }
    }

    private void saveBook() {
        this.validationMsg = "";
        setFields();
        if(bookDetailsValid()){
            JSONObject bookDtls = getBookDetailsObject();
//            TODO - JSONObjectRequest to save new book details

        } else {
            Toast.makeText(this, this.validationMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private void setFields() {
        bookTitle = (EditText) findViewById(R.id.ab_book_name);
        bookAuthor = (EditText) findViewById(R.id.ab_book_author);
        isbn13 = (EditText) findViewById(R.id.ab_isbn_13);
        isbn10 = (EditText) findViewById(R.id.ab_isbn_10);
        bookPublisher = (EditText) findViewById(R.id.ab_publisher);
        binding = (Spinner) findViewById(R.id.ab_binding);
        bookPrice = (EditText) findViewById(R.id.ab_price);
        tags = (EditText) findViewById(R.id.ab_book_tags);
        genre = (Spinner) findViewById(R.id.ab_book_genre);
        bookComments = (EditText) findViewById(R.id.ab_book_comments);
        condition = (RadioGroup) findViewById(R.id.ab_book_condition);
    }

    private JSONObject getBookDetailsObject() {
//        TODO - getting image for book and adding it to JSON
        JSONObject book = new JSONObject();
        try {
            book.put("isbn13", isbn13.getText().toString());
            book.put("isbn10", isbn10.getText().toString());
            book.put("name", bookTitle.getText().toString());

            List<String> bookTags = Arrays.asList(tags.getText().toString().split(","));
            book.put("tags", new JSONArray(bookTags));
            book.put("publishedOn", new Date().toString());

            List<JSONObject> authors = new ArrayList<JSONObject>();
            JSONObject newAutor = new JSONObject();
            newAutor.put("name", bookAuthor.getText().toString());
            newAutor.put("email", "dummy@gmail.com");
            authors.add(newAutor);
            book.put("authors", new JSONArray(authors));

            List<JSONObject> comments = new ArrayList<JSONObject>();
            JSONObject comment = new JSONObject();
            comment.put("body", bookComments.getText().toString());
            comments.add(comment);
            book.put("comments", new JSONArray(comments));

            JSONObject publisher = new JSONObject();
            publisher.put("name", bookPublisher.getText().toString());
            JSONObject address = new JSONObject();
            address.put("street1", "");
            address.put("street2", "");
            address.put("country", "");
            address.put("city", "");
            address.put("state", "");
            address.put("zip", "");
            publisher.put("address", address);
            List<String> publisherUrls = new ArrayList<String>();
            publisherUrls.add("dummy.com");
            publisher.put("url", new JSONArray(publisherUrls));
            book.put("publisher", publisher);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Rohan", book.toString());

        return book;
    }

    private boolean bookDetailsValid() {
//        TODO - validate all the book details entered
        return true;
    }
}
