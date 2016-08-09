package com.talentica.bookshelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rohanr on 8/9/16.
 */
public class DrawerFragment extends Fragment {

    public DrawerFragment(){
        Log.d("Rohan", "constructor");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.category_navigation, container, false);
        Log.d("Rohan", "create view");
        return vw;
    }

}
