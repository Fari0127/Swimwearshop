package com.example.swimwearshop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Product product = (Product) getIntent().getSerializableExtra("product");

        ImageView productImage = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        Button addToCartButton = findViewById(R.id.addToCartButton);

        if (product != null) {
            productImage.setImageResource(product.getImageResId());
            productName.setText(product.getName());
            productPrice.setText("$" + product.getPrice());
        }

        addToCartButton.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(product);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }
}