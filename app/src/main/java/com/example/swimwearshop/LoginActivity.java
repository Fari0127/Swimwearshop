package com.example.swimwearshop;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.content.Context;



public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        Button registerRedirect = findViewById(R.id.toRegisterButton);

        mAuth = FirebaseAuth.getInstance();

        // Animációk
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        emailInput.startAnimation(fadeIn);
        loginButton.startAnimation(slideIn);

        loginButton.setOnClickListener(v -> loginUser());

        registerRedirect.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showLoginNotification();
                        setDailyReminder();
                        startActivity(new Intent(LoginActivity.this, ProductListActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Sikertelen bejelentkezés", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showLoginNotification() {
        String channelId = "login_channel";
        String channelName = "Login Notifications";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bejelentkezés sikeres")
                .setContentText("Üdvözlünk a SwimwearShop alkalmazásban!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }

        NotificationManagerCompat.from(this).notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) { // 1-es kóddal kértük
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Engedély megadva – most már küldhetünk értesítést
                showLoginNotification(); // újra meghívjuk
            } else {
                Toast.makeText(this, "Az értesítési engedély megtagadva", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void setDailyReminder() {
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long triggerTime = getNext9AMTimeMillis();

        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

    private long getNext9AMTimeMillis() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 9);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
        }
        return calendar.getTimeInMillis();
    }


}
