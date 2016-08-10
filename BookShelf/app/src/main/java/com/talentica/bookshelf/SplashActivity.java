package com.talentica.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

        if(validStoredCredentials()) {
            intent = new Intent(ctx, NewMainActivity.class);
        } else {
            intent = new Intent(ctx, LoginActivity.class);
        }

        startActivity(intent);
        finish();

    }

    private boolean validStoredCredentials() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);
        String uToken = sharedPref.getString(Constants.USER_TOKEN, "");
        //TODO - check username n password with server and act accordingly
        return false;
    }

}
