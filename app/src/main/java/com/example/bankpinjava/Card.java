package com.example.bankpinjava;

public class Card { //Класс для хранения данных карт
    private String name; //Название карты
    private String pin; //pin-код карты

    public Card(String name, String pin) { //Конструктор
        this.name = name;
        this.pin = pin;
    }
    //Геттеры и сеттеры
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
