package com.talentica.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

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
//        check the stored credentials if available with server

        return false;
    }

}
