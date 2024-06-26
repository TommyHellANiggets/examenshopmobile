package com.example.examenshopmobile;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartItems;
    private AppDatabase db;
    private RecyclerView recyclerView;
    private TextView totalSumTextView;

    public CartAdapter(List<Cart> cartItems, AppDatabase db, RecyclerView recyclerView, TextView totalSumTextView) {
        this.cartItems = cartItems;
        this.db = db;
        this.recyclerView = recyclerView;
        this.totalSumTextView = totalSumTextView;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cartItem = cartItems.get(position);
        new LoadProductTask(holder).execute(cartItem.productId);
        holder.productQuantity.setText(String.valueOf(cartItem.quantity)); // Set initial quantity
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        EditText productQuantity;
        Button deleteButton;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            deleteButton = itemView.findViewById(R.id.delete_button);

            productQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String quantityText = s.toString();
                    if (quantityText.isEmpty()) {
                        productQuantity.setText("1");
                        quantityText = "1";
                    }
                    int quantity = Integer.parseInt(quantityText);
                    int position = getAdapterPosition();
                    cartItems.get(position).quantity = quantity;
                    new UpdateQuantityTask(cartItems.get(position).productId, quantity).execute();
                    updateTotalSum();
                }
            });

            deleteButton.setOnClickListener(v -> {
                new DeleteFromCartTask(getAdapterPosition()).execute(cartItems.get(getAdapterPosition()).productId);
            });
        }
    }

    private class LoadProductTask extends AsyncTask<Integer, Void, Product> {
        private CartViewHolder holder;

        LoadProductTask(CartViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected Product doInBackground(Integer... productIds) {
            int productId = productIds[0];
            return db.productDao().getProductById(productId);
        }

        @Override
        protected void onPostExecute(Product product) {
            holder.productName.setText(product.name);
            holder.productPrice.setText(String.format("%d ₽", product.price));

            RequestOptions requestOptions = new RequestOptions()
                    .error(R.drawable.default_image);

            Glide.with(holder.itemView.getContext())
                    .load(holder.itemView.getContext().getResources().getIdentifier(
                            product.imagePath.substring(0, product.imagePath.lastIndexOf('.')),
                            "drawable",
                            holder.itemView.getContext().getPackageName()))
                    .apply(requestOptions)
                    .into(holder.productImage);

            updateTotalSum();
        }
    }

    private class DeleteFromCartTask extends AsyncTask<Integer, Void, Void> {
        private int position;

        DeleteFromCartTask(int position) {
            this.position = position;
        }

        @Override
        protected Void doInBackground(Integer... productIds) {
            int productId = productIds[0];
            db.cartDao().deleteByProductId(productId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cartItems.remove(position);
            notifyItemRemoved(position);
            updateTotalSum();
        }
    }

    private class UpdateQuantityTask extends AsyncTask<Void, Void, Void> {
        private int productId;
        private int quantity;

        UpdateQuantityTask(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.cartDao().updateQuantity(productId, quantity);
            return null;
        }
    }

    private void updateTotalSum() {
        int totalSum = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            Cart cartItem = cartItems.get(i);
            CartViewHolder holder = (CartViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

            if (holder != null) {
                String quantityText = holder.productQuantity.getText().toString();
                if (quantityText.isEmpty()) {
                    quantityText = "1"; // Default quantity to 1 if the input is empty
                    holder.productQuantity.setText(quantityText); // Update the EditText
                }
                try {
                    int quantity = Integer.parseInt(quantityText);
                    int price = Integer.parseInt(holder.productPrice.getText().toString().replaceAll("[^0-9]", ""));
                    totalSum += quantity * price;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        totalSumTextView.setText(String.format("Сумма: %d ₽", totalSum));
    }
}
