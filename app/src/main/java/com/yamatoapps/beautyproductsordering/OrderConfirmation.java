package com.yamatoapps.beautyproductsordering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class OrderConfirmation extends AppCompatActivity {
FirebaseFirestore db = FirebaseFirestore.getInstance();
TextView tvPizzaName,tvOrderTotal;
ImageView ivProductImage;
String document_id = "";
Button btnExit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        tvPizzaName = findViewById(R.id.tvPizzaName);
        ivProductImage = findViewById(R.id.ivProductImage);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(view -> {
            finish();
        });

        document_id = getIntent().getStringExtra("document_id");
        db.collection("beauty_products").document(document_id).get().addOnSuccessListener(documentSnapshot -> {
            tvOrderTotal.setText(documentSnapshot.getDouble("price").toString());
            tvPizzaName.setText(documentSnapshot.getString("name"));
            Picasso.get().load(documentSnapshot.getString("image_url")).into(ivProductImage);
        });
    }
}