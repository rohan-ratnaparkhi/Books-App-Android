package com.talentica.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ctx = this;

        emailId = (EditText) findViewById(R.id.et_login_email);
        password = (EditText) findViewById(R.id.et_login_password);

        bSignIn = (Button) findViewById(R.id.btn_login_sign_in);
        bSignUp = (Button) findViewById(R.id.btn_login_sign_up);
        bForgotPwd = (Button) findViewById(R.id.btn_login_forgot_password);

        bSignIn.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        bForgotPwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_forgot_password:
                Intent showForgotPassword = new Intent(ctx, ForgotPasswordActivity.class);
                startActivity(showForgotPassword);
                break;
            case R.id.btn_login_sign_in:
                checkUserLogin();
                break;
            case R.id.btn_login_sign_up:
                Intent showSignUpForm = new Intent(ctx, SignUpActivity.class);
                startActivity(showSignUpForm);
                break;
        }
    }

    private void checkUserLogin() {
        final String username = emailId.getText().toString().trim();
        final String pwd = password.getText().toString().trim();

        if(isUsernameInvalid(username) || isPasswordInvalid(pwd)) {
            Toast.makeText(ctx, "Please enter valid username & password", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.USER_LOGIN_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();

                        displayHomePage();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displayLoginError();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_USERNAME, username);
                params.put(Constants.KEY_PASSWORD, pwd);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean isPasswordInvalid(String pwd) {
        if(pwd.equalsIgnoreCase("")){
            return true;
        }
        return  false;
    }

    private boolean isUsernameInvalid(String username) {
        if(username.equalsIgnoreCase("")){
            return true;
        }
        return false;
    }

    private void displayHomePage(){
        //store user credentials
        Intent showHomePage = new Intent(ctx, NewMainActivity.class);
        startActivity(showHomePage);
    }

    private void displayLoginError(){
        Toast.makeText(ctx, Constants.INVALID_CREDENTIALS, Toast.LENGTH_LONG).show();
    }
}
