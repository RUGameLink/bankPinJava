package com.example.bankpinjava.authdb;

public class MyAuth {
    public static final String TABLE_NAME = "login_password"; //имя таблицы
    public static final String _ID = "_id"; //колонка ид строкик в таблце
    public static final String TITLE = "password"; //Колонка пароля
    public static final String DB_NAME = "auth.db"; //Имя бд
    public static final int DB_VERSION = 1; //версия бд

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT)"; //Запрос создания таблицы в бд

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME; //Запрос удаления таблицы из бд
}
