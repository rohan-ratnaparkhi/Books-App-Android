package com.talentica.bookshelf;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class NewMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageButton nav_home;
    ImageButton nav_todo;
    ImageButton nav_add;
    ImageButton nav_notification;
    ImageButton nav_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu nvMenu = navigationView.getMenu();
        MenuItem totalCat = nvMenu.findItem(R.id.book_categories);
        totalCat.setTitle("Categories (31)");

        navigationView.setNavigationItemSelectedListener(this);


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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d("Rohan", "nav clicked");

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        resetToolbarIconsToDefault();
        switch (v.getId()){
            case R.id.toolbar_home:
//                displayHome();
                nav_home.setImageResource(R.drawable.icon_home_select);
                break;
            case R.id.toolbar_todo:
                nav_todo.setImageResource(R.drawable.icon_todo_select);
                break;
            case R.id.toolbar_add:
//                displayAddBook();
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
}
