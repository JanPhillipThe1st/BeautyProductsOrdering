package com.yamatoapps.beautyproductsordering;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends ArrayAdapter<Order> {

    public OrderAdapter(@NonNull Context context, ArrayList<Order> lightSoundGroups) {
        super(context, 0, lightSoundGroups);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Order item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_item, parent, false);
        }
        // Lookup view for data population
        TextView tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);
        TextView tvDateOrdered = (TextView) convertView.findViewById(R.id.tvDateOrdered);
        ImageView ivPackage = (ImageView) convertView.findViewById(R.id.ivBeautyProduct);
        Picasso.get().load(item.image_url).into(ivPackage);
        TextView tvProductPrice = (TextView) convertView.findViewById(R.id.tvProductPrice);
        // Populate the data into the template view using the data object
        tvProductName.setText("Product name: " + item.name);
        tvDateOrdered.setText("Date ordered: " + item.date_ordered.toLocaleString());
        tvProductPrice.setText("Price: " + item.price.toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
