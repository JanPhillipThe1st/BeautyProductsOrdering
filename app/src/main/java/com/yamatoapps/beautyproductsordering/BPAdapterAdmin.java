package com.yamatoapps.beautyproductsordering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BPAdapterAdmin extends ArrayAdapter<BeautyProduct> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public BPAdapterAdmin(@NonNull Context context, ArrayList<BeautyProduct> lightSoundGroups) {
        super(context, 0, lightSoundGroups);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BeautyProduct item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bp_item_admin, parent, false);
        }
        Button btnEdit,btnDelete;
        btnEdit = convertView.findViewById(R.id.btnEdit);
        btnDelete = convertView.findViewById(R.id.btnDelete);
        // Lookup view for data population
        TextView tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);
        ImageView ivPackage = (ImageView) convertView.findViewById(R.id.ivBeautyProduct);
        TextView tvProductPrice = (TextView) convertView.findViewById(R.id.tvProductPrice);
        // Populate the data into the template view using the data object
        Picasso.get().load(item.image_url).into(ivPackage);
        tvProductName.setText(item.name);
        tvProductPrice.setText(item.price.toString());
        btnDelete.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            alertDialogBuilder.setTitle("Delete Product");
            alertDialogBuilder.setMessage("Are you sure you want to delete this product?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {

                MaterialAlertDialogBuilder deleteDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                deleteDialogBuilder.setTitle("Delete success");
                deleteDialogBuilder.setMessage("Product deleted successfully!");
                deleteDialogBuilder.setPositiveButton("OK", (deleteDialogBuilderDialogInterface,j)->{
                    deleteDialogBuilderDialogInterface.dismiss();
                    Activity context = (Activity) parent.getContext();
                });
                db.collection("beauty_products").document(item.id).delete().addOnSuccessListener(unused -> {
                deleteDialogBuilder.create().show();
                dialogInterface.dismiss();
                });
            });
            alertDialogBuilder.create().show();
        });

        btnEdit.setOnClickListener(view -> {
                    Intent intent = new Intent(parent.getContext(), EditProduct.class);
                    intent.putExtra("document_id",item.id);
            parent.getContext().startActivity(intent);
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
