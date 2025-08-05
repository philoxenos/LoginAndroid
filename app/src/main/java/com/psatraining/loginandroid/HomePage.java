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
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home_page);

        initializeViews();
        initializeDatabase();
        setupRecyclerView();
        loadUsersFromDatabase();

    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView_users);
        userList = new ArrayList<>();
    }

    private void initializeDatabase() {
        dbHelper = new DatabaseHelper(this);
    }

    private void setupRecyclerView() {
        userAdapter = new UserAdapter(this, userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
    }

    private void loadUsersFromDatabase() {
        try {
            // Clear existing list
            userList.clear();

            // Get users from database
            List<UserModel> usersFromDb = dbHelper.getAllUsers();

            if (usersFromDb != null && !usersFromDb.isEmpty()) {
                userList.addAll(usersFromDb);
                userAdapter.updateUserList(userList);
                Toast.makeText(this, "Loaded " + userList.size() + " users", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No users found in database", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading users: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Refresh data when activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        loadUsersFromDatabase();
    }











}