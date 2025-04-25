package com.example.swimwearshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productList.add(new Product("Bikini Classic", 29.99, R.drawable.bikini1));
        productList.add(new Product("Beach Swimsuit", 35.99, R.drawable.bikini3));
        productList.add(new Product("Retro One Piece", 39.99, R.drawable.bikini3));

        adapter = new ProductAdapter(productList, product -> {
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        Button cartButton = findViewById(R.id.cartButton);
        cartButton.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
    }
}