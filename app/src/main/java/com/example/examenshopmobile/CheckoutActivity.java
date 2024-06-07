package com.example.examenshopmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView totalAmountTextView;
    private EditText nameEditText, phoneEditText, emailEditText, addressEditText;
    private Spinner paymentMethodSpinner;
    private Button confirmOrderButton;
    private String selectedPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        db = AppDatabase.getInstance(this);
        totalAmountTextView = findViewById(R.id.total_amount_text_view);

        nameEditText = findViewById(R.id.edit_text_name);
        phoneEditText = findViewById(R.id.edit_text_phone);
        emailEditText = findViewById(R.id.edit_text_email);
        addressEditText = findViewById(R.id.edit_text_address);
        paymentMethodSpinner = findViewById(R.id.spinner_payment_method);
        confirmOrderButton = findViewById(R.id.confirm_order_button);

        // Заполнение спиннера способами оплаты
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(adapter);

        // Обработчик выбора способа оплаты
        paymentMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedPaymentMethod = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Ничего не делаем, если ничего не выбрано
            }
        });

        new CalculateTotalTask().execute();

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PlaceOrderTask().execute();
            }
        });
    }

    private class CalculateTotalTask extends AsyncTask<Void, Void, Double> {
        @Override
        protected Double doInBackground(Void... voids) {
            List<Cart> cartItems = db.cartDao().getAllCartItems();
            double total = 0;
            for (Cart cart : cartItems) {
                Product product = db.productDao().getProductById(cart.productId);
                total += product.price * cart.quantity;
            }
            return total;
        }

        @Override
        protected void onPostExecute(Double total) {
            totalAmountTextView.setText("Итого: " + total + " руб.");
        }
    }

    private class PlaceOrderTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();

                // Проверка заполненности всех полей
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    return false; // Если хотя бы одно поле пустое, вернуть false
                }

                double totalAmount = Double.parseDouble(totalAmountTextView.getText().toString().replace("Итого: ", "").replace(" руб.", ""));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDateAndTime = sdf.format(new Date());

                Order order = new Order();
                order.name = name;
                order.phone = phone;
                order.email = email;
                order.address = address;
                order.totalAmount = totalAmount;
                order.orderDate = currentDateAndTime;
                order.orderNumber = generateOrderNumber();
                order.paymentMethod = selectedPaymentMethod; // Добавляем выбранный способ оплаты

                long orderId = db.orderDao().insertOrder(order);

                List<Cart> cartItems = db.cartDao().getAllCartItems();
                List<OrderItem> orderItems = new ArrayList<>();

                for (Cart cart : cartItems) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.orderId = (int) orderId;
                    orderItem.productId = cart.productId;
                    Product product = db.productDao().getProductById(cart.productId);
                    orderItem.quantity = cart.quantity;
                    orderItem.price = product.price;
                    orderItems.add(orderItem);
                }

                db.orderItemDao().insertOrderItems(orderItems);
                db.cartDao().clearCart();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Intent intent = new Intent(CheckoutActivity.this, PaymentSuccessActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(CheckoutActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String generateOrderNumber() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(90000000) + 10000000; // Генерация 8-значного числа
        return String.valueOf(number);
    }
}
