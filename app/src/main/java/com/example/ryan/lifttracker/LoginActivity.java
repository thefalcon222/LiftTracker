package com.example.ryan.lifttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText password;
    EditText username;
    ImageView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        login = (ImageView) findViewById(R.id.LoginButton);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == login.getId() && password.getText().toString().equals("123") &&
                username.getText().toString().equals("user")) {
            Intent intent = new Intent(this, MainActivity.class);

            this.startActivity(intent);
            finish();
        }
    }
}
