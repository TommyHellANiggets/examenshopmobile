package com.example.examenshopmobile;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Product> productList;
    private Context context;
    private AppDatabase db;

    public ProductAdapter(Context context, List<Product> productList, AppDatabase db) {
        this.context = context;
        this.productList = productList;
        this.db = db;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.sortButton.setOnClickListener(v -> {
            });
        } else if (holder instanceof ProductViewHolder) {
            Product product = productList.get(position - 1);
            ProductViewHolder productHolder = (ProductViewHolder) holder;
            productHolder.productName.setText(product.name);
            productHolder.productPrice.setText(String.format("%d ₽", product.price));

            RequestOptions requestOptions = new RequestOptions()
                    .error(R.drawable.default_image);

            Glide.with(context)
                    .load(context.getResources().getIdentifier(
                            product.imagePath.substring(0, product.imagePath.lastIndexOf('.')),
                            "drawable",
                            context.getPackageName()))
                    .apply(requestOptions)
                    .into(productHolder.productImage);

            productHolder.addToCartButton.setOnClickListener(v -> new AddToCartTask(db, product.id).execute());
        }
    }

    @Override
    public int getItemCount() {
        return productList.size() + 1;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        Button sortButton;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            sortButton = itemView.findViewById(R.id.sort_button);
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }
    }

    private static class AddToCartTask extends AsyncTask<Void, Void, Void> {
        private AppDatabase db;
        private int productId;

        AddToCartTask(AppDatabase db, int productId) {
            this.db = db;
            this.productId = productId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Cart cart = new Cart();
            cart.productId = productId;
            db.cartDao().insert(cart);
            return null;
        }
    }
}
