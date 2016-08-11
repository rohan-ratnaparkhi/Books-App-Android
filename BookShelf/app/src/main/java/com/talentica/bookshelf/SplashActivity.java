package com.talentica.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rohanr on 7/11/16.
 */
public class SplashActivity extends AppCompatActivity  {
    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ctx = this;
        Intent intent;
        super.onCreate(savedInstanceState);

        displayLoginPage();
    }

    private void displayLoginPage(){
        Intent intent = new Intent(ctx, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
