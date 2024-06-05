package com.example.examenshopmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView textName, textPhone, textEmail;
    private Button buttonMyOrders, buttonCart, buttonLogin;

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

        buttonMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на страницу авторизации
                Fragment loginFragment = new LoginFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, loginFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        User user = getUserInfo();
        textName.setText(user.getName());
        textPhone.setText(user.getPhone());
        textEmail.setText(user.getEmail());

        return view;
    }

    private User getUserInfo() {
        String name = "Иван Иванов";
        String phone = "+7 (123) 456-7890";
        String email = "ivan@example.com";
        return new User(name, phone, email);
    }
}
