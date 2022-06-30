package com.example.bankpinjava.pindb;

public class MyPin {
    public static final String TABLE_NAME = "pin"; //имя таблицы
    public static final String _ID = "_id"; //колонка ид строкик в таблце
    public static final String TITLE = "name"; //Колонка name
    public static final String SUBTITLE = "pin"; //Колонка pin
    public static final String DB_NAME = "pin.db"; //Имя бд
    public static final int DB_VERSION = 1;  //версия бд

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT, " +
            SUBTITLE + " TEXT)";  //Запрос создания таблицы в бд

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME; //Запрос удаления таблицы из бд
}
