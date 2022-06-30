package com.example.bankpinjava.pindb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class PinDbHelper extends SQLiteOpenHelper {
    public PinDbHelper(@Nullable Context context) { //Конструнтор для получения контекста активити из которой вызывается класс
        super(context, MyPin.DB_NAME, null, MyPin.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //Функция создания таблицы в бд
        sqLiteDatabase.execSQL(MyPin.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //Функция обновления таблицы в бд
        sqLiteDatabase.execSQL(MyPin.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
