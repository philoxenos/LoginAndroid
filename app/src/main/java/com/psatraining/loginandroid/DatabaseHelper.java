package com.psatraining.loginandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Database(entities = {UserModel.class}, version = 1)
public abstract class DatabaseHelper extends RoomDatabase{

    public abstract UserDao userDao();

    private static volatile DatabaseHelper INSTANCE;

    static DatabaseHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (DatabaseHelper.class){
                if (INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext()
                                    , DatabaseHelper.class, "LoginAndroid")
                            .build();
                }
            }
        }
        return INSTANCE;
    }



//    private static final String dbName = "LoginAndroid.db";
//    private static final int dbVersion = 1;
//    private static final String tblUsers= "users";
//
//    private static final String COLUMN_USER_ID = "user_id";
//    private static final String COLUMN_USER_NAME = "UserName";
//    private static final String COLUMN_USER_EMAIL = "UserEmail";
//    private static final String COLUMN_PASSWORD = "Password";
//    private static final String COLUMN_BIRTHDATE = "Birthdate";
//    private static final String COLUMN_DESCRIPTION = "Description";
//
//    private static final String CREATE_TABLE_USERS =
//            "CREATE TABLE " + tblUsers + " (" +
//                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_USER_NAME + " VARCHAR NOT NULL, " +
//                    COLUMN_USER_EMAIL + " VARCHAR NOT NULL, " +
//                    COLUMN_BIRTHDATE + " VARCHAR NOT NULL, " +
//                    COLUMN_PASSWORD + " VARCHAR NOT NULL, " +
//                    COLUMN_DESCRIPTION + " VARCHAR NOT NULL" +
//                    ");";
//
//    public DatabaseHelper(@Nullable Context context) {
//        super(context, dbName, null, dbVersion);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_TABLE_USERS);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + tblUsers);
//        onCreate(db);
//    }
//
//    public long addUser(UserModel user){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_NAME, user.getUserName());
//        values.put(COLUMN_USER_EMAIL, user.getUserEmail());
//        values.put(COLUMN_PASSWORD, user.getUserPassword()); // Already hashed when passed in!
//        values.put(COLUMN_BIRTHDATE, user.getBirthDate());
//        values.put(COLUMN_DESCRIPTION, user.getDescription());
//
//        long id = db.insert(tblUsers, null, values);
//        db.close();
//        return id;
//    }
//
//    public List<UserModel> getAllUsers(){
//        List<UserModel> userList = new ArrayList<>();
//        String selectQuery = "SELECT " + COLUMN_USER_ID + ", " +
//                COLUMN_USER_NAME + ", " +
//                COLUMN_USER_EMAIL + ", " +
//                COLUMN_BIRTHDATE + ", " +
//                COLUMN_DESCRIPTION +
//                " FROM " + tblUsers;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()){
//            do{
//                int userID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
//                String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
//                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
//                String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDATE));
//                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
//                userList.add(new UserModel(userID, userName, userEmail, "", birthdate, description));
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return userList;
//    }
//
//    public boolean validateUser(String userName, String password){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String hashedPassword = hashPassword(password);
//
//        String validateQuery = "SELECT * FROM " + tblUsers +
//                " WHERE " + COLUMN_USER_NAME + "=? AND " + COLUMN_PASSWORD + "=?";
//        Cursor cursor = db.rawQuery(validateQuery, new String[]{userName, hashedPassword});
//        boolean userExists = cursor.moveToFirst();
//
//        cursor.close();
//        db.close();
//        return userExists;
//    }
//
//    public boolean validateRegistration(String email){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String validateRegQuery = "SELECT * FROM " + tblUsers + " WHERE " + COLUMN_USER_EMAIL + " =?";
//        Cursor cursor = db.rawQuery(validateRegQuery, new String[]{email});
//        boolean regExists = cursor.moveToFirst();
//
//        cursor.close();
//        db.close();
//        return regExists;
//    }
//
//    public static String hashPassword(String password){
//        try{
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//
//            for (byte b: hash){
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1)
//                    hexString.append(0);
//                hexString.append(hex);
//            }
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}