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

            // To store the timestamp of each user
            long now = System.currentTimeMillis();

            // Hash the password
            String hashPassword = Utility.hashPassword(password);

            // Create an instance of UserModel
            UserModel user = new UserModel(username, email, hashPassword, birthdate, description, now, now);

            userRepository.checkEmailExists(email, exists -> runOnUiThread(() -> {
                if (exists) {
                    Toast.makeText(this, "Email already exists, try another.", Toast.LENGTH_SHORT).show();
                } else {
                    userRepository.registerUser(user, new retrofit2.Callback<UserModel>() {
                        @Override
                        public void onResponse(retrofit2.Call<UserModel> call, retrofit2.Response<UserModel> response) {
                            runOnUiThread(() -> {
                                if (response.isSuccessful()) {
                                    Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, Login.class));
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onFailure(retrofit2.Call<UserModel> call, Throwable t) {
                            runOnUiThread(() -> {
                                Toast.makeText(Register.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                }
            }));

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


}