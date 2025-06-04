package com.yamatoapps.beautyproductsordering;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    Uri fileUri;
    Button btnSaveProduct;
    TextView tvProductName
    ,tvProductPrice;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView iv = findViewById(R.id.ivProductImage);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data != null) {
            fileUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileUri);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_product);
        ImageView ivProductImage = findViewById(R.id.ivProductImage);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Intent imageIntent = new Intent();
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
       tvProductName  = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        ivProductImage.setOnClickListener(view -> {
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(imageIntent,"Pick image to upload"),22);

        });
        btnSaveProduct.setOnClickListener(view ->{
            uploadImage();
        });
    }
    public  void uploadImage(){
        if (fileUri != null){
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setMessage("Posting your Product...");
            progressDialog.show();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(UUID.randomUUID().toString());
            UploadTask uploadTask = (UploadTask) storageReference.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {

            }).addOnFailureListener(listener->{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to Upload Image..", Toast.LENGTH_SHORT)
                        .show();
            });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> listing = new HashMap<>();
                        listing.put("name", tvProductName.getText().toString());
                        listing.put("price",  Double.parseDouble(tvProductPrice.getText().toString()));
                        listing.put("image_url",  task.getResult());
                        DocumentReference newListingRef = db.collection("beauty_products").document();
                        newListingRef.set(listing);
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Congratulations! your Product was successfully posted!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        }
    }
}