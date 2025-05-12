package com.example.swimwearshop;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(productList, product -> {
            // semmit nem csinálunk kattintásra
        });
        recyclerView.setAdapter(adapter);

        loadProductsFilteredSortedLimited();
    }

    private void loadProductsFilteredSortedLimited() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("products")
                .whereEqualTo("category", "swimsuit")            // 1. szűrés
                .orderBy("price", Query.Direction.ASCENDING)     // 2. rendezés
                .limit(5)                                         // 3. limitálás
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    productList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Product product = document.toObject(Product.class);
                        product.setId(document.getId());
                        productList.add(product);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a lekérdezéskor", Toast.LENGTH_SHORT).show();
                });
    }
}
