package com.yamatoapps.beautyproductsordering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button back = findViewById(R.id.btnBack);
        Button btnSignup = findViewById(R.id.btnSignup);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView tvUsername, tvPassword,tvAddress,tvPhoneNumber;
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        Map<String, Object> map = new HashMap<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSignup.setOnClickListener( view -> {
            map.put("username",tvUsername.getText().toString());
            map.put("password",tvPassword.getText().toString());
            map.put("address",tvAddress.getText().toString());
            map.put("phone_number",tvPhoneNumber.getText().toString());
            map.put("type","customer");
            db.collection("beauty_products_users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Register.this);
                    alertBuilder.setTitle("Sign up");
                    alertBuilder.setMessage("Sucessfully Added User!");
                    alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    alertBuilder.create().show();
                }
            });
        });
    }
}