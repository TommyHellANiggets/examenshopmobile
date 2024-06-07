package com.example.examenshopmobile;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private AppDatabase db;
    private List<Product> cartProductList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new LoadCartTask(db, cartProducts -> {
            cartProductList.addAll(cartProducts);
            cartAdapter = new CartAdapter(CartActivity.this, cartProductList);
            recyclerView.setAdapter(cartAdapter);
        }).execute();
    }

    private static class LoadCartTask extends AsyncTask<Void, Void, List<Product>> {
        private AppDatabase db;
        private OnCartLoadedListener listener;

        LoadCartTask(AppDatabase db, OnCartLoadedListener listener) {
            this.db = db;
            this.listener = listener;
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            List<Cart> cartItems = db.cartDao().getAllCartItems();
            List<Product> products = new ArrayList<>();
            for (Cart cartItem : cartItems) {
                Product product = db.productDao().getProductById(cartItem.productId);
                if (product != null) {
                    products.add(product);
                }
            }
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            listener.onCartLoaded(products);
        }
    }

    interface OnCartLoadedListener {
        void onCartLoaded(List<Product> cartProducts);
    }
}
