package com.talentica.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    Context signUpCtx;
    EditText fullName;
    EditText email;
    EditText pwd;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fullName = (EditText) findViewById(R.id.et_signup_name);
        email = (EditText) findViewById(R.id.et_signup_email);
        pwd = (EditText) findViewById(R.id.et_signup_pwd);
        signUp = (Button) findViewById(R.id.btn_sign_up);

        signUp.setOnClickListener(this);
    }


    private void registerUser(){
        final String username = fullName.getText().toString().trim();
        final String uEmail = email.getText().toString().trim();
        final String uPassword = pwd.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.USER_SIGN_UP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(isSuccessResponse(response)){
                            try {
                                storeUserCredentials(response, uEmail, uPassword);
//      TODO - on successful registration, goto login page and use sign up credentials to login the user
                                displayLoginPage();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                displaySignUpError();
                            }
                        } else {
                            Toast.makeText(signUpCtx, "Some error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displaySignUpError();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_USERNAME, username);
                params.put(Constants.KEY_EMAIL, uEmail);
                params.put(Constants.KEY_PASSWORD, uPassword);
//                TODO - if roles are to be specified then add sending roles logic below
//                String roles = "[{\"name\":\"string\"}]";
//                params.put(Constants.KEY_ROLES, roles);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Constants.APP_TOKEN);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_sign_up){
            registerUser();
        }
    }

    private void displayLoginPage(){
        finish();
    }

    private boolean isSuccessResponse(String response){
        //TODO - validate if response is as per expectation or not
        return true;
    }

    private void storeUserCredentials(String response, String username, String pwd) throws JSONException {
        JSONObject res = new JSONObject(response);
        JSONObject data = res.getJSONObject("data");
        String userId = data.getString(Constants.KEY_USER_ID);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_profile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.KEY_USER_ID, userId);
        editor.putString(Constants.KEY_USERNAME, username);
        editor.putString(Constants.KEY_PASSWORD, pwd);
        editor.commit();
    }

    private void displaySignUpError(){
        Toast.makeText(signUpCtx, Constants.ERROR_OCCURRED, Toast.LENGTH_LONG).show();
    }
}
