package com.learn.niceboy.themcc.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learn.niceboy.themcc.R;

public class AdminLogin extends AppCompatActivity {

    EditText inputPass, inputUser;
    TextInputLayout inputLayoutPass, inputLayoutUser;
    Button signIn;

    String textPass, textUser, userName, password;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        sharedpreferences = getSharedPreferences("Admin Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Username", "themcc7");
        editor.putString("Password", "TheMcc7");
        editor.commit();

        getSupportActionBar().setTitle("Admin Login");

        inputLayoutUser = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputUser = (EditText) findViewById(R.id.input_username);

        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputPass = (EditText) findViewById(R.id.input_password);

        signIn = (Button) findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage();
            }
        });
    }

    //Validating form
    private void nextPage() {

        // For validation Edittext
        if (!validateUsername()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        userName = sharedpreferences.getString("Username", null); // getting String
        password = sharedpreferences.getString("Password", null);

        if (userName.equals(inputUser.getText().toString()) && password.equals(inputPass.getText().toString())) {

            Intent intent = new Intent(getApplicationContext(), AdminEvents.class);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Please check your Username or Password !", Toast.LENGTH_LONG).show();
        }
    }


    private boolean validateUsername() {
        if (inputUser.getText().toString().trim().isEmpty()) {
            inputLayoutUser.setError(getString(R.string.err_msg_user));
            requestFocus(inputUser);
            return false;
        } else {
            textUser = inputUser.getText().toString();
            inputLayoutUser.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (inputPass.getText().toString().trim().isEmpty()) {
            inputLayoutPass.setError(getString(R.string.err_msg_password));
            requestFocus(inputPass);
            return false;
        } else {
            textPass = inputPass.getText().toString();
            inputLayoutPass.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
