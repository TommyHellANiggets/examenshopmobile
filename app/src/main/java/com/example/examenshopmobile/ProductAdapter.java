package com.example.examenshopmobile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;
    private static final String TAG = "ProductAdapter";
    private AppDatabase db;

    public ProductAdapter(Context context, List<Product> productList, AppDatabase db) {
        this.context = context;
        this.productList = productList;
        this.db = db;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.name);
        holder.productPrice.setText(String.format("%d руб.", product.price));

        // Логирование
        Log.d(TAG, "Product name: " + product.name);
        Log.d(TAG, "Image path: " + product.imagePath);

        // Получаем идентификатор ресурса изображения по имени файла без расширения
        String imageName = product.imagePath.substring(0, product.imagePath.lastIndexOf('.'));
        int imageResource = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        if (imageResource == 0) {
            Log.d(TAG, "Image resource not found for " + product.imagePath);
        } else {
            Log.d(TAG, "Image resource found: " + imageResource);
        }

        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.default_image); // Установите изображение по умолчанию в случае ошибки

        Glide.with(context)
                .load(imageResource != 0 ? imageResource : R.drawable.default_image)
                .apply(requestOptions)
                .into(holder.productImage);

        holder.addToCartButton.setOnClickListener(v -> {
            // Добавление в корзину
            new AddToCartTask(db, product.id).execute();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
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
