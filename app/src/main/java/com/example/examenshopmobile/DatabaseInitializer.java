package com.example.examenshopmobile;

import android.os.AsyncTask;

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addProduct(final AppDatabase db, final String name, final int quantity, final int price, final String description, final String imagePath) {
        Product product = new Product();
        product.name = name;
        product.quantity = quantity;
        product.price = price;
        product.description = description;
        product.imagePath = imagePath;
        db.productDao().insert(product);
    }

    private static void populateWithTestData(AppDatabase db) {
        if (db.productDao().getAllProducts().isEmpty()) {
            addProduct(db, "Protein Powder", 50, 2999, "High-quality whey protein powder.", "protein_powder.jpg");
            addProduct(db, "Creatine Monohydrate", 30, 1499, "Micronized creatine for muscle growth.", "creatine.jpg");
            addProduct(db, "BCAA", 40, 1999, "Branched-Chain Amino Acids for muscle recovery.", "bcaa.jpg");
            addProduct(db, "Pre-Workout", 20, 2499, "Pre-workout supplement for energy boost.", "pre_workout.jpg");
            addProduct(db, "Multivitamin", 60, 999, "Complete multivitamin for overall health.", "multivitamin.jpg");
            addProduct(db, "Omega-3", 45, 1299, "Fish oil supplement rich in Omega-3.", "omega_3.jpg");
            addProduct(db, "Weight Gainer", 10, 3499, "High-calorie weight gainer for mass building.", "weight_gainer.jpg");
            addProduct(db, "Casein Protein", 25, 2799, "Slow-digesting casein protein.", "casein_protein.jpg");
            addProduct(db, "Glutamine", 35, 1599, "L-glutamine for muscle recovery.", "drawable/glutamine.jpg");
            addProduct(db, "Beta-Alanine", 15, 1799, "Beta-alanine for improved performance.", "beta_alanine.jpg");
        }
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }
    }
}
