package com.example.swimwearshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button registerButton = findViewById(R.id.registerButton);
        Button loginRedirect = findViewById(R.id.toLoginButton);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(v -> registerUser());

        loginRedirect.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, ProductListActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Hiba a regisztráció során", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
