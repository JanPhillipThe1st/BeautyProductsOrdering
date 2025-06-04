package com.yamatoapps.beautyproductsordering.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yamatoapps.beautyproductsordering.AddProduct;
import com.yamatoapps.beautyproductsordering.BPAdapter;
import com.yamatoapps.beautyproductsordering.BPAdapterAdmin;
import com.yamatoapps.beautyproductsordering.BeautyProduct;
import com.yamatoapps.beautyproductsordering.R;
import com.yamatoapps.beautyproductsordering.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        GridView gvItems =binding.gvCard;
        Button addNewProduct = binding.btnAddProduct;
        ArrayList<BeautyProduct> items = new ArrayList<>();
        BPAdapterAdmin bpAdapterAdmin = new BPAdapterAdmin(binding.getRoot().getContext(), items);
        addNewProduct.setOnClickListener(view ->{
        startActivity(new Intent(binding.getRoot().getContext(), AddProduct.class));
        });
        db.collection("beauty_products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                items.clear();
                bpAdapterAdmin.clear();
                for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                    bpAdapterAdmin.add(new BeautyProduct(documentSnapshot.getString("name"),documentSnapshot.getDouble("price"),documentSnapshot.getString("image_url"), documentSnapshot.getId()));

                }
                gvItems.setAdapter(bpAdapterAdmin);
            }
        });
        gvItems.setAdapter(bpAdapterAdmin);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}