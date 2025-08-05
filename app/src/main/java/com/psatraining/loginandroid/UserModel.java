package com.psatraining.loginandroid;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int userID;

    @NonNull
    @ColumnInfo(name = "user_name")
    private String userName;

    @NonNull
    @ColumnInfo(name = "user_email")
    private String userEmail;

    @NonNull
    @ColumnInfo(name = "user_password")
    private String userPassword;

    @NonNull
    @ColumnInfo(name = "user_birthDate")
    private String birthDate;

    @NonNull
    @ColumnInfo(name = "user_desc")
    private String description;

    @ColumnInfo(name = "created_at")
    private long createdAt; // long is already non-null

    @ColumnInfo(name = "last_updated_at")
    private long lastUpdatedAt; // long is already non-null

    public UserModel(){

    }

    // Constructor without ID (for new users - ID will be auto-generated)
    public UserModel(String userName, String userEmail, String userPassword, String birthDate, String description, long createdAt, long lastUpdatedAt) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.birthDate = birthDate;
        this.description = description;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    // Constructor with ID (for existing users from database)
    public UserModel(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(long lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
