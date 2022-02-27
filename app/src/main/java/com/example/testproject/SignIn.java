package com.example.testproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SignIn extends Activity {
    AlertDialog alertDialog;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        // Backbutton
        ((Button) findViewById(R.id.back_button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, MainActivity.class);
                SignIn.this.startActivity(i);
            }
        });

        ((Button) findViewById(R.id.btnsignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, SignUp.class);
                SignIn.this.startActivity(i);
            }
        });
    }

    public void OnLogin(View view) {                  //OnLogin method to Login
        EditText etEmail = (EditText) findViewById(R.id.txtSignInEmail);
        EditText etPwd = (EditText) findViewById(R.id.txtSignInPassword);
        String email = etEmail.getText().toString();
        String password = etPwd.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, email, password);
        finish();
    }
}
