package com.psatraining.loginandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText et_regEmail, et_regUsername, et_regPassword, et_birthdate, et_description;
    ImageView iv_calendar;
    Button btn_Register;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_register);

        et_regEmail = findViewById(R.id.et_regEmail);
        et_regUsername = findViewById(R.id.et_regUsername);
        et_regPassword = findViewById(R.id.et_regPassword);
        et_birthdate = findViewById(R.id.et_birthdate);
        et_description = findViewById(R.id.et_description);
        iv_calendar = findViewById(R.id.iv_calendar);
        btn_Register = findViewById(R.id.btn_Register);

        userRepository = new UserRepository(this);


        iv_calendar.setOnClickListener(v -> showDatePickerDialog());

        btn_Register.setOnClickListener(v -> {
            String email = et_regEmail.getText().toString().trim();
            String username = et_regUsername.getText().toString().trim();
            String password = et_regPassword.getText().toString().trim();
            String birthdate = et_birthdate.getText().toString().trim();
            String description = et_description.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || birthdate.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hash password for network and local
            String hashedPassword = hashPassword(password);

            // To store the timestamp of each user
            long now = System.currentTimeMillis();

            // Create an instance of UserModel
            UserModel user = new UserModel(username, email, hashedPassword, birthdate, description, now, now);




            // Check remote for email existence
            userClient.findUserByEmail(email).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isEmpty()) {

                        // Not on server, add remotely (send hashed password)

                        long now = System.currentTimeMillis();
                        UserModel user = new UserModel(username, email, hashedPassword, birthdate, description, now, now);

                        if (userDao.validateRegUser(email) == null) {
                            userDao.insertUser(user);
                        }
                        else {
                            Toast.makeText(Register.this, "Email already exists locally, try another.", Toast.LENGTH_SHORT).show();
                            return;
                        }






                        userClient.addUser(user).enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                if (response.isSuccessful()) {
                                    // Save locally (hashed password)
                                    dbHelper.addUser(user);
                                    Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, Login.class));
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserModel> call, Throwable t) {
                                Toast.makeText(Register.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "Email already exists, try another.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserModel>> call, Throwable t) {
                    Toast.makeText(Register.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Register.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    et_birthdate.setText(date);
                },
                year, month, day);

        datePickerDialog.show();
    }

    public static String hashPassword(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b: hash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append(0);
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}