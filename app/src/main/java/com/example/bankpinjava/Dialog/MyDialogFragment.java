package com.example.bankpinjava.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MyDialogFragment extends AppCompatDialogFragment {
    private String password;

    public MyDialogFragment(String password) { //Конструктор диалога
        this.password = password;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //Создание билдера для диалога
        builder.setTitle("Важное сообщение!") //Установка заголовка
                .setMessage("Ваш пароль: " + password + "\nНе забывайте его!") //Установка текста
                .setPositiveButton("ОК, не забуду", new DialogInterface.OnClickListener() { //Слушатель positiveButton
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем окно
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

}
