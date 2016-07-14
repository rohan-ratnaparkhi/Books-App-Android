package com.talentica.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Context ctx;

    EditText emailId;
    EditText password;

    Button bSignIn;
    Button bForgotPwd;
    Button bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;

        bSignIn = (Button) findViewById(R.id.btn_login_sign_in);
        bSignUp = (Button) findViewById(R.id.btn_login_sign_up);
        bForgotPwd = (Button) findViewById(R.id.btn_login_forgot_password);

        bSignIn.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        bForgotPwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_forgot_password:
                Intent showForgotPassword = new Intent(ctx, ForgotPasswordActivity.class);
                startActivity(showForgotPassword);
                break;
            case R.id.btn_login_sign_in:
                break;
            case R.id.btn_login_sign_up:
                break;
        }
    }
}
