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

public class RegistrationFragment extends Fragment {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPhone, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister, buttonBackToLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        // Находим элементы управления в макете
        editTextFirstName = view.findViewById(R.id.edit_text_first_name);
        editTextLastName = view.findViewById(R.id.edit_text_last_name);
        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextPhone = view.findViewById(R.id.edit_text_phone);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        editTextConfirmPassword = view.findViewById(R.id.edit_text_confirm_password);
        buttonRegister = view.findViewById(R.id.button_register);
        buttonBackToLogin = view.findViewById(R.id.button_back_to_login);

        // Устанавливаем обработчик для кнопки "Зарегистрироваться"
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Здесь будет обработка нажатия кнопки "Зарегистрироваться"
            }
        });

        // Устанавливаем обработчик для кнопки "Вернуться на страницу авторизации"
        buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на страницу авторизации
                Fragment loginFragment = new LoginFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, loginFragment)
                        .commit();
            }
        });

        return view;
    }
}
