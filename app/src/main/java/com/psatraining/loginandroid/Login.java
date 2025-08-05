package com.psatraining.loginandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText et_Username, et_Password;
    TextView tvBtn_Register;
    Button btn_Login;
    UserClient userClient;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        et_Username = findViewById(R.id.et_Username);
        et_Password = findViewById(R.id.et_Password);
        tvBtn_Register = findViewById(R.id.tvBtn_Register);
        btn_Login = findViewById(R.id.btn_Login);

        userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);
        dbHelper = new DatabaseHelper(this);

        btn_Login.setOnClickListener(v -> {
            String username = et_Username.getText().toString().trim();
            String password = et_Password.getText().toString().trim();

            boolean exists = dbHelper.validateUser(username, password);

            if (exists){
                Intent intent = new Intent(Login.this, HomePage.class );
                startActivity(intent);
                finish();
            } else{
                Toast.makeText(this, "Incorrect Username or Password!", Toast.LENGTH_SHORT).show();
            }
        });

        tvBtn_Register.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class );
            startActivity(intent);
        });
    }
}