package com.psatraining.loginandroid;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserModel> userList;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView_users);
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this, userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);

        userRepository = new UserRepository(this);
        loadUsers();
    }

    private void loadUsers() {
        userRepository.getAllUsers().observe(this, users -> {
            userList.clear();
            if (users != null && !users.isEmpty()) {
                userList.addAll(users);
                userAdapter.updateUserList(userList);
                Toast.makeText(this, "Loaded " + userList.size() + " users", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No users found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Refresh data when activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }

}