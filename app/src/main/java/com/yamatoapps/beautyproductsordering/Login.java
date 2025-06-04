package com.yamatoapps.beautyproductsordering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnLogin = findViewById(R.id.btnLogin);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView tvUsername,tvPassword;
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnLogin.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(Login.this);
            progressDialog.setTitle("Login");
            progressDialog.setMessage("Logging in. Please wait...");
            progressDialog.show();
            db.collection("beauty_products_users")
                    .where(Filter.and(
                            Filter.equalTo("username",tvUsername.getText().toString()),
                            Filter.equalTo("password",tvPassword.getText().toString()),
                            Filter.equalTo("type","admin")
                    )).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.size() > 0){
                                startActivity(new Intent(Login.this,AdminPanel.class));
                                progressDialog.dismiss();
                                Toast.makeText(Login.this,"Logged in as admin",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                db.collection("beauty_products_users")
                                        .where(Filter.and(
                                                Filter.equalTo("username",tvUsername.getText().toString()),
                                                Filter.equalTo("password",tvPassword.getText().toString()),
                                                Filter.equalTo("type","customer")
                                        )).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshotsCustomer) {
                                                if (queryDocumentSnapshotsCustomer.size() > 0){
                                                    progressDialog.dismiss();
                                                    Intent catalogueIntent = new Intent(Login.this,BPCatalogue.class);
                                                    startActivity(catalogueIntent);
                                                    Toast.makeText(Login.this,"Logged in as customer",Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                                    builder.setTitle("Invalid Login");
                                                    builder.setMessage("No user found");
                                                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            tvUsername.setText("");
                                                            tvPassword.setText("");
                                                            dialogInterface.dismiss();
                                                        }
                                                    });
                                                    builder.create().show();
                                                }
                                            }
                                        });
                            }
                        }
                    });
        });
    }
}