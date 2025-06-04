package com.yamatoapps.beautyproductsordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                        sleep(4000);
                        Intent loginRegisterIntent = new Intent(getBaseContext(), LoginRegister.class);
                        startActivity(loginRegisterIntent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}