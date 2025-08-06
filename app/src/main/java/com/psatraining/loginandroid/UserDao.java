package com.psatraining.loginandroid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserModel userModel);

    @Update
    void updateUser(UserModel userModel);

    @Delete
    void deleteUser(UserModel userModel);

    @Query("SELECT * FROM user_table")
    List<UserModel> getAllUsers();

    @Query("SELECT * FROM user_table WHERE user_name = :userName AND user_password = :userPassword")
    UserModel validateUser(String userName, String userPassword);

    @Query("SELECT * FROM user_table WHERE user_email = :userEmail LIMIT 1")
    UserModel validateRegUser(String userEmail);
}