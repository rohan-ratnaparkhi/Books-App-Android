package com.talentica.bookshelf;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton nav_home;
    ImageButton nav_todo;
    ImageButton nav_add;
    ImageButton nav_notification;
    ImageButton nav_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav_home = (ImageButton) findViewById(R.id.toolbar_home);
        nav_todo = (ImageButton) findViewById(R.id.toolbar_todo);
        nav_add = (ImageButton) findViewById(R.id.toolbar_add);
        nav_notification = (ImageButton) findViewById(R.id.toolbar_notification);
        nav_profile = (ImageButton) findViewById(R.id.toolbar_profile);

        nav_home.setOnClickListener(this);
        nav_todo.setOnClickListener(this);
        nav_add.setOnClickListener(this);
        nav_notification.setOnClickListener(this);
        nav_profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        resetToolbarIconsToDefault();
        switch (v.getId()){
            case R.id.toolbar_home:
                displayHome();
                nav_home.setImageResource(R.drawable.icon_home_select);
                break;
            case R.id.toolbar_todo:
                nav_todo.setImageResource(R.drawable.icon_todo_select);
                break;
            case R.id.toolbar_add:
                displayAddBook();
                nav_add.setImageResource(R.drawable.icon_add_select);
                break;
            case R.id.toolbar_notification:
                nav_notification.setImageResource(R.drawable.icon_notification_select);
                break;
            case R.id.toolbar_profile:
                nav_profile.setImageResource(R.drawable.icon_profile_select);
                break;
        }
    }

    void resetToolbarIconsToDefault(){
        nav_home.setImageResource(R.drawable.icon_home);
        nav_todo.setImageResource(R.drawable.icon_todo);
        nav_add.setImageResource(R.drawable.icon_add);
        nav_notification.setImageResource(R.drawable.icon_notification);
        nav_profile.setImageResource(R.drawable.icon_profile);
    }

    void displayHome(){
        DrawerFragment homeFragment = new DrawerFragment();
        Log.d("Rohan", "after construction");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, homeFragment).commit();
        Log.d("Rohan", "after transaction");
    }

    void displayAddBook(){
        AddBookFragment addBookFragment = new AddBookFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, addBookFragment).commit();
    }

}
