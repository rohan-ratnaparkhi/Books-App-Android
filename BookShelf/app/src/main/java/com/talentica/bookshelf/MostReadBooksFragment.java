package com.talentica.bookshelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rohanr on 8/22/16.
 */
public class MostReadBooksFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_books, container, false);
        TextView title = (TextView) view.findViewById(R.id.title_for_list);
        title.setText(getString(R.string.most_read_title));
        displayMostReadBooksList();
        return view;
    }

    private void displayMostReadBooksList() {

    }
}
