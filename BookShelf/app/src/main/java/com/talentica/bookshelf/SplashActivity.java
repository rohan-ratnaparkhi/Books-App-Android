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
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(ctx, LoginActivity.class);
        startActivity(intent);
        finish();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(ctx, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 5000);

    }
}
