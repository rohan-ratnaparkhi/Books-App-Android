package com.talentica.bookshelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rohanr on 8/11/16.
 */
public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_option, container, false);
        loadRecentlyAddedBooks();
        loadMostReadBooks();
        return view;
    }

    private void loadMostReadBooks() {
        MostReadBooksFragment mostRead = new MostReadBooksFragment();
        FragmentManager manager = this.getFragmentManager();
        manager.beginTransaction().add(R.id.most_read_books, mostRead).commit();
    }

    private void loadRecentlyAddedBooks() {
        RecentlyAddedBooksFragment recentBooks = new RecentlyAddedBooksFragment();
        FragmentManager manager = this.getFragmentManager();
        manager.beginTransaction().add(R.id.recently_added_books, recentBooks).commit();
    }
}
