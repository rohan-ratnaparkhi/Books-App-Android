package com.talentica.bookshelf;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rohanr on 8/22/16.
 */
public class RecentlyAddedBooksFragment extends Fragment {

    SharedPreferences sharedPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_books, container, false);
        TextView title = (TextView) view.findViewById(R.id.title_for_list);
        title.setText(getString(R.string.recently_added_title));
        sharedPref = getContext().getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);
//        displayRecentlyAddedBooksList();
        return view;
    }


}
