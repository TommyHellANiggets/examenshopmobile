package com.example.examenshopmobile;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class UserRepository {

    private UserDao userDao;

    public UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public void insert(User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public LiveData<User> getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    public LiveData<User> getUserById(int id) {
        return userDao.getUserById(id);
    }
}
