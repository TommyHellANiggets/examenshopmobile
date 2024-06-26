package com.example.examenshopmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private EditText editTextPhoneNumber, editTextPassword;
    private Button buttonLogin, buttonBackToProfile, buttonRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextPhoneNumber = view.findViewById(R.id.edit_text_phone_number);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        buttonLogin = view.findViewById(R.id.button_login);
        buttonBackToProfile = view.findViewById(R.id.button_back_to_profile);
        buttonRegister = view.findViewById(R.id.button_register);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String password = editTextPassword.getText().toString();

                if (checkLogin(phoneNumber, password)) {
                    Fragment profileFragment = new ProfileFragment();
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, profileFragment)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Неверный номер телефона или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment profileFragment = new ProfileFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, profileFragment)
                        .commit();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment registrationFragment = new RegistrationFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, registrationFragment)
                        .commit();
            }
        });

        return view;
    }

    private boolean checkLogin(String phoneNumber, String password) {
        return true;
    }
}
