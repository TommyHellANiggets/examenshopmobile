package com.example.examenshopmobile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public LiveData<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public LiveData<User> getUserByPhone(String phone) {
        return userRepository.getUserByPhone(phone);
    }

    public LiveData<User> getUserById(int id) {
        return userRepository.getUserById(id);
    }
}
