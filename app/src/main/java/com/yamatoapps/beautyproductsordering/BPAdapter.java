package com.yamatoapps.beautyproductsordering;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.yamatoapps.beautyproductsordering.BeautyProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BPAdapter extends ArrayAdapter<BeautyProduct> {

    public BPAdapter(@NonNull Context context, ArrayList<BeautyProduct> lightSoundGroups) {
        super(context, 0, lightSoundGroups);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BeautyProduct item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bp_item, parent, false);
        }
        // Lookup view for data population
        TextView tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);
        Button btnBuy = convertView.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String,Object> map = new HashMap<>();
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
            builder.setTitle("Success");
            builder.setMessage("Order Successfully Placed. Thank you!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent order_confirmation_intent = new Intent(parent.getContext(),OrderConfirmation.class);
                    order_confirmation_intent.putExtra("document_id",item.id);
                    parent.getContext().startActivity(order_confirmation_intent);
                    dialogInterface.dismiss();

                }
            });
            map .put("name",item.name);
            map .put("image_url",item.image_url);
            map .put("price",item.price);
            map .put("date_ordered", Calendar.getInstance().getTime());
            db.collection("beauty_orders").add(map).addOnSuccessListener(documentReference -> {
                builder.create().show();
            });
        });
        ImageView ivPackage = (ImageView) convertView.findViewById(R.id.ivBeautyProduct);
        Picasso.get().load(item.image_url).into(ivPackage);
        TextView tvProductPrice = (TextView) convertView.findViewById(R.id.tvProductPrice);
        // Populate the data into the template view using the data object
        tvProductName.setText(item.name);
        tvProductPrice.setText(item.price.toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
