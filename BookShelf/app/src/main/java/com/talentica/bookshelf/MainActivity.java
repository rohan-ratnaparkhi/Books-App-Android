package com.talentica.bookshelf;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AHBottomNavigation.OnTabSelectedListener {

    ImageButton bar_home, bar_todo;
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar_home = (ImageButton) findViewById(R.id.toolbar_home);
        bar_todo = (ImageButton) findViewById(R.id.toolbar_todo);
        bar_home.setOnClickListener(this);
        bar_todo.setOnClickListener(this);
//        bottomNavigation= (AHBottomNavigation) findViewById(R.id.bottom_navigation);
//        bottomNavigation.setOnTabSelectedListener(this);
//        this.createNavItems();
    }

//    private void createNavItems() {
//        //CREATE ITEMS
//        AHBottomNavigationItem crimeItem = new AHBottomNavigationItem("", R.drawable.icon_home_select);
//        AHBottomNavigationItem crimeItems = new AHBottomNavigationItem("", R.drawable.icon_home);
////        AHBottomNavigationItem dramaItem = new AHBottomNavigationItem("Drama", R.drawable.ac);
////        AHBottomNavigationItem docstem = new AHBottomNavigationItem("Crime", R.drawable.dr);
//
//        //ADD THEM to bar
//        bottomNavigation.addItem(crimeItem);
//        bottomNavigation.addItem(crimeItems);
////        bottomNavigation.
//        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#aaaaaa"));
//        bottomNavigation.setAccentColor(Color.parseColor("#dddddd"));
//        bottomNavigation.setForceTint(true);
//        bottomNavigation.setCurrentItem(0);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_home:
                resetToolbarIconsToDefault();
                bar_home.setImageResource(R.drawable.icon_home_select);
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_todo:
                resetToolbarIconsToDefault();
                bar_todo.setImageResource(R.drawable.icon_todo_select);
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void resetToolbarIconsToDefault(){
        bar_home.setImageResource(R.drawable.icon_home);
        bar_todo.setImageResource(R.drawable.icon_todo);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        return false;
    }
}
