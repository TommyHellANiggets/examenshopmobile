package com.example.examenshopmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonBackToProfile, buttonRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Находим элементы управления в макете
        editTextUsername = view.findViewById(R.id.edit_text_username);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        buttonLogin = view.findViewById(R.id.button_login);
        buttonBackToProfile = view.findViewById(R.id.button_back_to_profile);
        buttonRegister = view.findViewById(R.id.button_register);

        // Устанавливаем обработчик для кнопки "Войти"
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Здесь будет обработка нажатия кнопки "Войти"
            }
        });

        // Устанавливаем обработчик для кнопки "Вернуться в профиль"
        buttonBackToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на страницу профиля
                Fragment profileFragment = new ProfileFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, profileFragment)
                        .commit();
            }
        });

        // Устанавливаем обработчик для кнопки "Регистрация"
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на страницу регистрации
                Fragment registrationFragment = new RegistrationFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, registrationFragment)
                        .commit();
            }
        });

        return view;
    }
}
