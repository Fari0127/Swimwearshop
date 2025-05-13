
package com.example.swimwearshop;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FirestoreQueries {

    private static final String TAG = "FirestoreQueries";

    public static void executeQueries() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 1. Kategória szűrés és ár szerint rendezés
        db.collection("products")
                .whereEqualTo("category", "swimwear")
                .orderBy("price")
                .limit(10)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots ->
                        Log.d(TAG, "Query 1: " + queryDocumentSnapshots.size() + " results"));

        // 2. Név szerint keresés és korlátozás
        db.collection("products")
                .whereGreaterThan("name", "B")
                .orderBy("name")
                .limit(5)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots ->
                        Log.d(TAG, "Query 2: " + queryDocumentSnapshots.size() + " results"));

        // 3. Ár tartomány lekérdezés
        db.collection("products")
                .whereGreaterThanOrEqualTo("price", 20)
                .whereLessThanOrEqualTo("price", 100)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots ->
                        Log.d(TAG, "Query 3: " + queryDocumentSnapshots.size() + " results"));
    }
}
