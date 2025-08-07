package com.psatraining.loginandroid;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {

    private final UserDao userDao;
    private final UserClient userClient;
    private final Context context;

    public UserRepository(Context context) {
        this.context = context.getApplicationContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this.context);
        this.userDao = dbHelper.userDao();
        this.userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);

    }

    public LiveData<List<UserModel>> getAllUsers(){

        MutableLiveData<List<UserModel>> usersLiveData = new MutableLiveData<>();

        userClient.getAllUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                // If api connection is successful
                if (response.isSuccessful() && response.body() != null){
                    usersLiveData.postValue(response.body());

                } else {
                    // In case of no internet connection, will save to Room
                    Executors.newSingleThreadExecutor().execute(() ->{
                        usersLiveData.postValue(userDao.getAllUsers());
                    });
                }


            }
            // In case of no internet connection, will save to Room
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Executors.newSingleThreadExecutor().execute(() ->{
                    usersLiveData.postValue(userDao.getAllUsers());
                });
            }
        });
        return usersLiveData;
    }

    // Remote register, then local on success (User must connect to internet to register)
    public void registerUser(UserModel user, Callback<UserModel> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            long generatedId = userDao.insertUser(user);
            user.setId((int) generatedId);
            // Now send to server with correct id
            userClient.addUser(user).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    callback.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    callback.onFailure(call, t);
                }
            });
        });
    }

    // Check if email exists (network first, then Room)
    public void checkEmailExists(String email, EmailCheckCallback callback) {
        userClient.findUserByEmail(email).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                boolean exists = response.isSuccessful() && response.body() != null && !response.body().isEmpty();
                if (!exists) {
                    // Fallback to Room
                    Executors.newSingleThreadExecutor().execute(() -> {
                        UserModel user = userDao.validateRegUser(email);
                        callback.onResult(user != null);
                    });
                } else {
                    callback.onResult(true);
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                // On network failure, check local
                Executors.newSingleThreadExecutor().execute(() -> {
                    UserModel user = userDao.validateRegUser(email);
                    callback.onResult(user != null);
                });
            }
        });
    }

    // Login (network first, then Room)
    public void login(String username, String password, LoginCallback callback) {
        userClient.findUserByNameAndPassword(username, password).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                boolean success = response.isSuccessful() && response.body() != null && !response.body().isEmpty();
                if (!success) {
                    // Fallback to Room
                    Executors.newSingleThreadExecutor().execute(() -> {
                        UserModel user = userDao.validateUser(username, password);
                        callback.onResult(user != null);
                    });
                } else {
                    callback.onResult(true);
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                // Fallback to Room
                Executors.newSingleThreadExecutor().execute(() -> {
                    UserModel user = userDao.validateUser(username, password);
                    callback.onResult(user != null);
                });
            }
        });
    }

    // --- Callback interfaces ---
    public interface EmailCheckCallback {
        void onResult(boolean exists);
    }
    public interface LoginCallback {
        void onResult(boolean success);
    }
}
