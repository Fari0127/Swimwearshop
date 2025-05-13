package com.example.swimwearshop;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import android.widget.Toast;


public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        TextView cartItemsText = findViewById(R.id.cartItemsText);
        List<Product> cartItems = CartManager.getInstance().getCartItems();

        StringBuilder builder = new StringBuilder();
        double total = 0;
        for (Product product : cartItems) {
            builder.append(product.getName())
                   .append(" - $").append(product.getPrice()).append("\n");
            total += product.getPrice();
        }
        builder.append("\nTotal: $").append(total);

        cartItemsText.setText(builder.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Kosár mentése folyamatban...", Toast.LENGTH_SHORT).show();
    }
}