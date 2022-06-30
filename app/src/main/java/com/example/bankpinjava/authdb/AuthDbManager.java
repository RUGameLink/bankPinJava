package com.example.bankpinjava.authdb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AuthDbManager {
    private Context context; //Переменная для хранения контекста
    private AuthDbHelper authDbHelper; //Инициализация класса-хелпера
    private SQLiteDatabase sqLiteDatabase; //Инициализация класса для работы с sqllite

    public AuthDbManager(Context context) {//Конструктор класса с привязкой к контексту
        this.context = context;
        authDbHelper = new AuthDbHelper(context);//Инициализация переменной хелпера с контекстом
    }

    public void openDb(){ //Функция открытия бд
        sqLiteDatabase = authDbHelper.getWritableDatabase();
    }

    public void insertToDb(String password){ //Функция записи данных в бд
        ContentValues contentValues = new ContentValues(); //Инициализация переменной для внесения значений
        contentValues.put(MyAuth.TITLE, password);//Внесение значений
        sqLiteDatabase.insert(MyAuth.TABLE_NAME, null, contentValues); //Запись в бд
    }

    public String readFromDb(){ //Считывание из бд
        String result = "";
        Cursor cursor = sqLiteDatabase.query(MyAuth.TABLE_NAME //Инициализация курсора
                , null
                , null
                , null
                , null
                , null
                , null);
        while (cursor.moveToNext()){ //Пока следующий элемент в курсоре есть то
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(MyAuth.TITLE));//Считывание в переменную из столбца
            result = password; //Запись в переменную result
        }
        cursor.close(); //Закрытие курсора
        return result; //Возврат результата
    }

    public void closeDb(){ //Закрытие бд
        authDbHelper.close();
    }
}
