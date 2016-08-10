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

//        if(validStoredCredentials()) {
//            intent = new Intent(ctx, NewMainActivity.class);
//        } else {
//            intent = new Intent(ctx, LoginActivity.class);
//        }
//
//        startActivity(intent);
//        finish();

        displayLoginOrHome();

    }

    private void displayLoginOrHome() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);
        String uToken = sharedPref.getString(Constants.USER_TOKEN, "");
        final String username = sharedPref.getString(Constants.KEY_USERNAME, "");
        final String password = sharedPref.getString(Constants.KEY_PASSWORD, "");
        //TODO - check username n password with server and act accordingly
        if(username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            displayLoginPage();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.USER_LOGIN_API,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                            if(isSuccessResponse(response)){
                                try {
                                    storeUserCredentials(response, username, password);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                displayHomePage();
                            } else {
                                Toast.makeText(ctx, "Some error occurred", Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            displayLoginPage();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(Constants.KEY_USERNAME, username);
                    params.put(Constants.KEY_PASSWORD, password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    private void displayLoginPage(){
        Intent intent = new Intent(ctx, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isSuccessResponse(String response){
        //TODO - validate if response is as per expectation or not
        return true;
    }

    private void storeUserCredentials(String response, String username, String pwd) throws JSONException {
        JSONObject res = new JSONObject(response);
        String uToken = res.getString("data");
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.USER_TOKEN, uToken);
        editor.putString(Constants.KEY_USERNAME, username);
        editor.putString(Constants.KEY_PASSWORD, pwd);
        editor.commit();
    }

    private void displayHomePage(){
        Intent intent = new Intent(ctx, NewMainActivity.class);
        startActivity(intent);
        finish();
    }

}
