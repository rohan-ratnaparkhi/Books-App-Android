package com.talentica.bookshelf;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    Context forgotPwdCtx;
    EditText email;
    Button forgotPassword;
    private int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPwdCtx = this;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        email = (EditText) findViewById(R.id.et_forgot_pwd_email);
        forgotPassword = (Button) findViewById(R.id.btn_forgot_password);
        forgotPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_forgot_password){
            sendPasswordResetLink();
        }
    }

    private void sendPasswordResetLink() {
        final String username = email.getText().toString().trim();

        if(isUsernameInvalid(username)) {
            Toast.makeText(forgotPwdCtx, "Please enter valid email id", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.USER_FORGOT_PWD_API + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        displayMessage(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displayErrorMessage();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_USERNAME, username);
                return params;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("Rohan", "status = " + mStatusCode);
                saveStatusCode(mStatusCode);
                return super.parseNetworkResponse(response);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void displayMessage(String response) {
        if(this.statusCode == 200){
            Toast.makeText(forgotPwdCtx, Constants.PASSWORD_RESET_MAIL_SENT, Toast.LENGTH_LONG).show();
        }
    }

    private void saveStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private void displayErrorMessage() {
        if(this.statusCode == 404){
            Toast.makeText(forgotPwdCtx, Constants.USER_NOT_FOUND, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(forgotPwdCtx, Constants.ERROR_OCCURRED, Toast.LENGTH_LONG).show();
        }

    }

    private boolean isUsernameInvalid(String username) {
        if(username.equalsIgnoreCase("")){
            return true;
        }
        return false;
    }

    private boolean isSuccessResponse(String response){
        //TODO - validate if response is as per expectation or not
        return true;
    }
}
