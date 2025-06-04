package com.yamatoapps.beautyproductsordering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BPCatalogue extends AppCompatActivity {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpcatalogue);
        ArrayList<BeautyProduct> beautyProducts= new ArrayList<BeautyProduct>();
        BPAdapter adapter = new BPAdapter(this, beautyProducts);
        db.collection("beauty_products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                adapter.add(new BeautyProduct(documentSnapshot.getString("name"),documentSnapshot.getDouble("price"),documentSnapshot.getString("image_url"),documentSnapshot.getId()));

                }
                GridView gridView = (GridView) findViewById(R.id.gvCard);
                gridView.setAdapter(adapter);
            }
        });

    }
}