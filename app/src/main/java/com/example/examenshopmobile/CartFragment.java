package com.example.examenshopmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartFragment extends Fragment {
    private AppDatabase db;
    private RecyclerView recyclerView;
    private TextView totalSumTextView;
    private Button checkoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        db = AppDatabase.getInstance(getContext());
        recyclerView = view.findViewById(R.id.recycler_view_cart);
        totalSumTextView = view.findViewById(R.id.total_sum_text_view);
        checkoutButton = view.findViewById(R.id.checkout_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new LoadCartTask().execute();

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCheckout();
            }
        });

        return view;
    }

    private class LoadCartTask extends AsyncTask<Void, Void, List<Cart>> {
        @Override
        protected List<Cart> doInBackground(Void... voids) {
            return db.cartDao().getAllCartItems();
        }

        @Override
        protected void onPostExecute(List<Cart> cartItems) {
            CartAdapter adapter = new CartAdapter(cartItems, db, recyclerView, totalSumTextView);
            recyclerView.setAdapter(adapter);
        }
    }

    private void navigateToCheckout() {
        Intent intent = new Intent(getActivity(), CheckoutActivity.class);
        startActivity(intent);
    }
}
