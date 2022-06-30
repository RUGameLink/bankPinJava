package com.example.bankpinjava.authdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AuthDbHelper extends SQLiteOpenHelper {
    public AuthDbHelper(@Nullable Context context) { //Конструнтор для получения контекста активити из которой вызывается класс
        super(context, MyAuth.DB_NAME, null, MyAuth.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //Функция создания таблицы в бд
        sqLiteDatabase.execSQL(MyAuth.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//Функция обновления таблицы в бд
        sqLiteDatabase.execSQL(MyAuth.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
