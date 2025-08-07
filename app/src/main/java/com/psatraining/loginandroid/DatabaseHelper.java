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

}