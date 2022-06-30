package com.example.bankpinjava.pindb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.example.bankpinjava.Card;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PinManager {
    private Context context; //Переменная для хранения контекста
    private PinDbHelper pinDbHelper; //Инициализация класса-хелпера
    private SQLiteDatabase sqLiteDatabase; //Инициализация класса для работы с sqllite

    public PinManager(Context context) { //Конструктор класса с привязкой к контексту
        this.context = context;
        pinDbHelper = new PinDbHelper(context); //Инициализация переменной хелпера с контекстом
    }

    public void openDb(){ //Функция открытия бд
        sqLiteDatabase = pinDbHelper.getWritableDatabase();
    }

    public void insertToDb(String name,String pin){//Функция записи данных в бд
        ContentValues contentValues = new ContentValues();//Инициализация переменной для внесения значений
        contentValues.put(MyPin.TITLE, name);//Внесение значений
        contentValues.put(MyPin.SUBTITLE, pin);//Внесение значений
        sqLiteDatabase.insert(MyPin.TABLE_NAME, null, contentValues);//Запись в бд
    }

    public void deleteFromDB(){ //Функция очистки бд
        sqLiteDatabase.execSQL(MyPin.DROP_TABLE); //Удаление таблицы из бд
        sqLiteDatabase.execSQL(MyPin.CREATE_TABLE); //Создание новой таблицы в бд
    }

    public List<Card> readFromDb(){//Считывание из бд
        List<Card> result = new ArrayList<>(); //Инициализация листа для результатов
        Cursor cursor = sqLiteDatabase.query(MyPin.TABLE_NAME //Инициализация курсора
                , null
                , null
                , null
                , null
                , null
                , null);
        while (cursor.moveToNext()){ //Пока следующий элемент в курсоре есть то
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MyPin.TITLE));//Считывание в переменную из столбца
            @SuppressLint("Range") String pin = cursor.getString(cursor.getColumnIndex(MyPin.SUBTITLE));//Считывание в переменную из столбца
            String codeWord = "codeWord"; //Кодовое слово для дешифратора
            try {
                String dName = decrypt(name, codeWord); //дешифровка переменной name
                String dPin = decrypt(pin, codeWord); //дешифровка переменной pin
                result.add(new Card(dName, dPin)); //Добавление дешифрованных данных в лист
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        cursor.close();//Закрытие курсора
        return result; //Возврат результата
    }

    private String decrypt(String name, String codeWord) throws Exception{ //Функиця дешифровки данных
        SecretKey key = generateKey(codeWord); //Генерация ключа по кодовому слову
        Cipher cipher = Cipher.getInstance("AES"); //Инициализация класса алгоритма шифрования с заданием системы защиты
        cipher.init(Cipher.DECRYPT_MODE, key); //Инициализация функционала
        byte[] decodedValue = Base64.decode(name, Base64.DEFAULT); //байтовый массив дл яхранения результата
        byte[] decValue = cipher.doFinal(decodedValue); //Проход по зашифрованным данным и дешифровка
        String decryptedValue = new String(decValue); //Запись в переменную результата расшифровки
        return decryptedValue; //Возврат результата
    }

    private SecretKey generateKey(String codeWord) throws Exception{ //Функция генерации ключа для расшифровки по кодовому слову
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = codeWord.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    public void closeDb(){//Закрытие бд
        pinDbHelper.close();
    }
}
