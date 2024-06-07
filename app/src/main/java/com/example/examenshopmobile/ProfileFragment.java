package com.example.examenshopmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView textName, textPhone, textEmail;
    private Button buttonMyOrders, buttonCart, buttonLogin;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textName = view.findViewById(R.id.text_name);
        textPhone = view.findViewById(R.id.text_phone);
        textEmail = view.findViewById(R.id.text_email);
        buttonMyOrders = view.findViewById(R.id.button_my_orders);
        buttonCart = view.findViewById(R.id.button_cart);
        buttonLogin = view.findViewById(R.id.button_login);

        sharedPreferences = getActivity().getSharedPreferences("current_user", Context.MODE_PRIVATE);
        loadUserProfile();

        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход во фрагмент корзины
                Fragment cartFragment = new CartFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, cartFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        buttonMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход во фрагмент моих заказов
                Fragment myOrdersFragment = new MyOrdersFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, myOrdersFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new LoginFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, loginFragment)
                        .commit();
            }
        });

        return view;
    }

    private void loadUserProfile() {
        int userId = sharedPreferences.getInt("user_id", -1);
        if (userId != -1) {
            String firstName = sharedPreferences.getString("user_first_name", "Неизвестно");
            String lastName = sharedPreferences.getString("user_last_name", "Неизвестно");
            String email = sharedPreferences.getString("user_email", "Неизвестно");
            String phone = sharedPreferences.getString("user_phone", "Неизвестно");

            textName.setText(firstName + " " + lastName);
            textEmail.setText(email);
            textPhone.setText(phone);
        } else {
            textName.setText("Войдите в аккаунт");
            textEmail.setText("");
            textPhone.setText("");
            Toast.makeText(getContext(), "Войдите в аккаунт", Toast.LENGTH_SHORT).show();
        }
    }
}
