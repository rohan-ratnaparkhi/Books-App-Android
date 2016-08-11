package com.talentica.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by rohanr on 8/9/16.
 */
public class AddBookFragment extends Fragment implements View.OnClickListener {
    Button btnAddBookMan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_book_option, container, false);
        btnAddBookMan = (Button) view.findViewById(R.id.add_book_manually);
        btnAddBookMan.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_book_manually){
            Toast.makeText(getContext(), "reached", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getContext(), AddBookDetailsActivity.class);
            startActivity(intent);
        }

    }
}
