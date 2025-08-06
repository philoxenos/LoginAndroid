package com.psatraining.loginandroid;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") // Changed from "user_id" to "id" to match json-server
    private int id; // Changed from userID to id

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
    private long createdAt;

    @ColumnInfo(name = "last_updated_at")
    private long lastUpdatedAt;

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

    // Getters and setters - updated method names
    public int getId() { // Changed from getUserID()
        return id;
    }

    public void setId(int id) { // Changed from setUserID()
        this.id = id;
    }

    // For backward compatibility, keep the old method names but delegate to new ones
    public int getUserID() {
        return getId();
    }

    public void setUserID(int userID) {
        setId(userID);
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