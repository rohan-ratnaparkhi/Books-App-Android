package com.talentica.bookshelf;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talentica.bookshelf.Adapter.HomeListAdapter;
import com.talentica.bookshelf.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO - replacing previous fragments not add them on each other

public class NewMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageButton nav_home;
    ImageButton nav_todo;
    ImageButton nav_add;
    ImageButton nav_notification;
    ImageButton nav_profile;
    RecyclerView rv_recently_added;
    RecyclerView rv_most_read;
    Context c;
    ArrayList<Book> bookList;

    SharedPreferences sharedPref;

    ArrayList<Book> recentlyAdded;
    ArrayList<Book> mostRead;
    HomeListAdapter homeListAdapter1, homeListAdapter2;

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

        c = this;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        sharedPref = getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);

        displayHome();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        TODO - check this as these are null on actual device
        rv_recently_added = (RecyclerView) findViewById(R.id.main_container).findViewById(R.id.recently_added_books).findViewById(R.id.rv_list_of_books);
        rv_most_read = (RecyclerView) findViewById(R.id.main_container).findViewById(R.id.most_read_books).findViewById(R.id.rv_list_of_books);
//      TODO - replace urls when they work properly for most read and recently added
//        presently writing dummy logic for this
        recentlyAdded = new ArrayList<Book>();
        mostRead = new ArrayList<Book>();
        displayBooksList(Constants.BASE_URL + Constants.ALL_BOOKS_API, listType.RECENTLY_ADDED);

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
//        final MenuItem item = menu.findItem(R.id.search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setIconifiedByDefault(false);
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

        displaySelectedGenreBooks(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedGenreBooks(MenuItem item) {
//                TODO - get list of books of this genre and display in gridview
        StringRequest req = new StringRequest(Request.Method.GET,
                Constants.BASE_URL + Constants.ALL_BOOKS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Rohan", response.toString());

                        displayBooksGrid(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Rohan", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("limit", "50");
                params.put("page", "1");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + sharedPref.getString(Constants.USER_TOKEN, ""));
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }

    private void displayBooksGrid(String response) {
        try {
            bookList = new ArrayList<Book>();
            JSONObject json = new JSONObject(response);
            JSONObject data = json.getJSONObject("data");
            JSONArray allBooks = data.getJSONArray("docs");
            for(int i=0; i<allBooks.length(); i++){
                JSONObject bookObj = allBooks.getJSONObject(i);
                Book book = new Book();
                book.setBookId(bookObj.getString("_id"));
                book.setBookName(bookObj.getString("name"));
                JSONArray authors = bookObj.getJSONArray("authors");
                if(authors.length() > 0){
                    JSONObject author = authors.getJSONObject(0);
                    book.setAuthorName(author.getString("name"));
                }
                book.setLenderName(bookObj.getJSONObject("publisher").getString("name"));
                book.setBookImageUrl("https://upload.wikimedia.org/wikipedia/en/5/59/Hulk_(comics_character).png");
                bookList.add(book);
            }
            if(bookList.size() > 0){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        resetToolbarIconsToDefault();
        switch (v.getId()) {
            case R.id.toolbar_home:
                displayHome();
                break;
            case R.id.toolbar_todo:
                nav_todo.setImageResource(R.drawable.icon_todo_select);
                break;
            case R.id.toolbar_add:
                nav_add.setImageResource(R.drawable.icon_add_select);
                displayAddBook();
                break;
            case R.id.toolbar_notification:
                nav_notification.setImageResource(R.drawable.icon_notification_select);
                break;
            case R.id.toolbar_profile:
                nav_profile.setImageResource(R.drawable.icon_profile_select);
                break;
        }
    }

    private void displayHome() {
        HomeFragment home = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, home).commit();
        nav_home.setImageResource(R.drawable.icon_home_select);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void displayAddBook() {
        AddBookFragment addBookFragment = new AddBookFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_container, addBookFragment).commit();
    }


    void resetToolbarIconsToDefault() {
        nav_home.setImageResource(R.drawable.icon_home);
        nav_todo.setImageResource(R.drawable.icon_todo);
        nav_add.setImageResource(R.drawable.icon_add);
        nav_notification.setImageResource(R.drawable.icon_notification);
        nav_profile.setImageResource(R.drawable.icon_profile);
    }

    enum listType {
        RECENTLY_ADDED,
        MOST_READ
    }

    private void displayBooksList(String urlToUse, final listType type) {
        try {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                    urlToUse,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Rohan", "1: " + response.toString());
                            createListFromResponse(response, type);
                            setAdapterForBooks(type);
                            displayMostReadBooksList(Constants.BASE_URL + Constants.ALL_BOOKS_API, listType.MOST_READ);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Rohan", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Constants.KEY_PAGE, "1");
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + sharedPref.getString(Constants.USER_TOKEN, ""));
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(req);
        } catch (Exception e) {
            Log.d("Rohan", e.toString());
        }
    }

    private void displayMostReadBooksList(String urlToUse, final listType type) {
        try {
            JsonObjectRequest req2 = new JsonObjectRequest(Request.Method.GET,
                    urlToUse,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Rohan", "2: " + response.toString());
                            createListFromResponse(response, type);
                            setAdapterForBooks(type);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Rohan", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Constants.KEY_PAGE, "2");
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + sharedPref.getString(Constants.USER_TOKEN, ""));
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(req2);
        } catch (Exception e) {
            Log.d("Rohan", e.toString());
        }
    }


    private void setAdapterForBooks(listType type) {
        if (type == listType.RECENTLY_ADDED && recentlyAdded.size() > 0) {
            homeListAdapter1 = new HomeListAdapter(c, recentlyAdded);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
            rv_recently_added.setLayoutManager(mLayoutManager);
            rv_recently_added.setAdapter(homeListAdapter1);
        } else if (type == listType.MOST_READ && mostRead.size() > 0) {
            homeListAdapter2 = new HomeListAdapter(c, mostRead);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
            rv_most_read.setLayoutManager(mLayoutManager);
            rv_most_read.setAdapter(homeListAdapter2);
        }
    }

    private void createListFromResponse(JSONObject response, listType type) {
        try {

            JSONObject data = response.getJSONObject("data");
            JSONArray books = data.getJSONArray("docs");
            for (int i = 0; i < books.length(); i++) {
                JSONObject bk = books.getJSONObject(i);
                JSONObject publisher = bk.getJSONObject("publisher");
                JSONArray authors = bk.getJSONArray("authors");
                JSONObject author = authors.getJSONObject(0);
                Book book = new Book();
                book.setBookName(bk.getString("name"));
                book.setLenderName(publisher.getString("name"));
                book.setAuthorName(author.getString("name"));
                book.setBookId(bk.getString("_id"));
                if (type == listType.MOST_READ) {
                    mostRead.add(book);
                    Log.d("Rohan", "type = 2");
                } else if (type == listType.RECENTLY_ADDED) {
                    recentlyAdded.add(book);
                    Log.d("Rohan", "type = 1");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
