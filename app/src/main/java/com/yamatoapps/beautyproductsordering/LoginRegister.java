package com.yamatoapps.beautyproductsordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Intent registerIntent = new Intent(this, Register.class);
        Intent loginIntent = new Intent(this, Login.class);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(registerIntent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(loginIntent);
            }
        });
    }
}