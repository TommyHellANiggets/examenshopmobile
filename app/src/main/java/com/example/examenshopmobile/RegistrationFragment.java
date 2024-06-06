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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class RegistrationFragment extends Fragment {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPhone, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister, buttonBackToLogin;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        editTextFirstName = view.findViewById(R.id.edit_text_first_name);
        editTextLastName = view.findViewById(R.id.edit_text_last_name);
        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextPhone = view.findViewById(R.id.edit_text_phone);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        editTextConfirmPassword = view.findViewById(R.id.edit_text_confirm_password);
        buttonRegister = view.findViewById(R.id.button_register);
        buttonBackToLogin = view.findViewById(R.id.button_back_to_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (password.equals(confirmPassword)) {
                    validateAndRegisterUser(firstName, lastName, email, phone, password);
                } else {
                    Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
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

    private void validateAndRegisterUser(String firstName, String lastName, String email, String phone, String password) {
        userViewModel.getUserByEmail(email).observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User userByEmail) {
                if (userByEmail != null) {
                    Toast.makeText(getContext(), "Этот email уже зарегистрирован", Toast.LENGTH_SHORT).show();
                } else {
                    userViewModel.getUserByPhone(phone).observe(getViewLifecycleOwner(), new Observer<User>() {
                        @Override
                        public void onChanged(User userByPhone) {
                            if (userByPhone != null) {
                                Toast.makeText(getContext(), "Этот номер телефона уже зарегистрирован", Toast.LENGTH_SHORT).show();
                            } else {
                                User user = new User();
                                user.setFirstName(firstName);
                                user.setLastName(lastName);
                                user.setEmail(email);
                                user.setPhone(phone);
                                user.setPassword(password);

                                userViewModel.insert(user);
                                Toast.makeText(getContext(), "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                                clearObservers();
                            }
                        }
                    });
                }
            }
        });
    }

    private void clearObservers() {
        // Clear observers after registration to prevent multiple triggers
        userViewModel.getUserByEmail("").removeObservers(getViewLifecycleOwner());
        userViewModel.getUserByPhone("").removeObservers(getViewLifecycleOwner());
    }
}
