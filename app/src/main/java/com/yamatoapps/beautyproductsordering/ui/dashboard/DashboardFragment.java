package com.yamatoapps.beautyproductsordering.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yamatoapps.beautyproductsordering.BPAdapterAdmin;
import com.yamatoapps.beautyproductsordering.BeautyProduct;
import com.yamatoapps.beautyproductsordering.Order;
import com.yamatoapps.beautyproductsordering.OrderAdapter;
import com.yamatoapps.beautyproductsordering.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        GridView gvItems =binding.gvCard;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<Order> items = new ArrayList<>();
        OrderAdapter adapter = new OrderAdapter(binding.getRoot().getContext(), items);
        db.collection("beauty_orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                adapter.add(new Order(documentSnapshot.getString("name"),documentSnapshot.getDouble("price"),documentSnapshot.getString("image_url"),
                        documentSnapshot.getId(),documentSnapshot.getDate("date_ordered", DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)));
                }
                gvItems.setAdapter(adapter);
            }
        });
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}