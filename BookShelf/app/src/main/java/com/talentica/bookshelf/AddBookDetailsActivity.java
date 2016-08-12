package com.talentica.bookshelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class AddBookDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    Button cancelAction;
    Button submitAction;
    String validationMsg;

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
                break;
            case R.id.btn_add_book_submit:
                saveBook();
                break;
        }
    }

    private void saveBook() {
        if(bookDetailsValid()){
            JSONObject bookDtls = getBookDetailsObject();

        } else {
            Toast.makeText(this, this.validationMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private JSONObject getBookDetailsObject() {
//        TODO - create object to send in post
        JSONObject book = new JSONObject();

        return book;
    }

    private boolean bookDetailsValid() {
//        TODO - validate all the book details entered
        return false;
    }
}
