package com.talentica.bookshelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddBookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_details);

//        Temporary Remove this Rohan
        displayAddBookDetails();
    }

    private void displayAddBookDetails() {
        BookDetailsManuallyFragment manualDtls = new BookDetailsManuallyFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.prev_or_new_details, manualDtls).commit();
    }
}
