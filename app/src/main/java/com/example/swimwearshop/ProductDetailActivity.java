package com.example.swimwearshop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class ProductDetailActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button updateButton, deleteButton;
    private String productId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Feltételezzük, hogy a ProductListActivity küldi a termék azonosítóját
        productId = getIntent().getStringExtra("productId");

        nameEditText = findViewById(R.id.productName);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        updateButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString();
            if (newName.isEmpty()) {
                Toast.makeText(this, "Adj meg új nevet!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("products")
                    .document(productId)
                    .update("name", newName)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Frissítve", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba frissítéskor", Toast.LENGTH_SHORT).show();
                    });
        });

        deleteButton.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("products")
                    .document(productId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Termék törölve", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba történt", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
