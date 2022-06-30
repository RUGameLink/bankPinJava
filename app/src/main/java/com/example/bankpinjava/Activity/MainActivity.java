package com.example.bankpinjava.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bankpinjava.Card;
import com.example.bankpinjava.R;
import com.example.bankpinjava.pindb.PinManager;
import com.example.bankpinjava.recyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    //Создание пременных объектов активити
    private FloatingActionButton addPinButton;
    private List<Card> cardList;
    private RecyclerView cardView;
    private Button deleteAllButton;

    private PinManager pinManager; //Создание переменной менеджера

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardList = new ArrayList<>();
        init();
        pinManager = new PinManager(this);
        addPinButton.setOnClickListener(addPinButtonClickListener);
        deleteAllButton.setOnClickListener(deleteAllButtonListener);
    }

    private void init(){
        addPinButton = findViewById(R.id.addPinButton);
        cardView = findViewById(R.id.cardView);
        deleteAllButton = findViewById(R.id.deleteAllButton);
    }

    View.OnClickListener deleteAllButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pinManager.deleteFromDB();
            setAdapter();
        }
    };

    View.OnClickListener addPinButtonClickListener = new View.OnClickListener() { //Слушатель кнопки добавления карты
        @Override
        public void onClick(View v) {
            //Создание кастомного алертдиалога
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getText(R.string.add_card)); //Заголовок диалога
            View view = getLayoutInflater().inflate(R.layout.dialog_add_pin, null); //Привязка разметки
            builder.setView(view);
            //Инициализация объектов с лайаута и привязка к кнопкам
            EditText nameCardText = view.findViewById(R.id.nameCardText);
            EditText pinCardText = view.findViewById(R.id.pinCardText);
            Button addButton = view.findViewById(R.id.addButton);

            AlertDialog dialog = builder.create(); //Создание диалога
            dialog.show(); //Запуск диалога
                    addButton.setOnClickListener(new View.OnClickListener() { //Слушатель кнопки добавить на диалоге
                        @Override
                        public void onClick(View view) {
                            if(nameCardText.getText().length() != 0 && pinCardText.getText().length() == 4) { //Если имя карты не пустое и длина pin равна 4
                                sendDialogDataToActivity(nameCardText.getText().toString(), pinCardText.getText().toString()); //Вызов метода передачи в активити
                                dialog.dismiss(); //Закрытие диалога
                            }
                            else
                                Toast.makeText(getApplicationContext(), getText(R.string.not_full_text), Toast.LENGTH_SHORT).show(); //Тост о неполноте данных
                        }
                    });
            }
    };

    private void setAdapter(){ //Функция загрузки данных в ресайклер
        cardList = pinManager.readFromDb(); //Считывание данных в лист из дб
        recyclerAdapter recyclerAdapter = new recyclerAdapter(cardList); //Создание ресайклер адаптера
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()); //Инициализация его менеджера
        cardView.setLayoutManager(layoutManager); //Установка менеджера в ресайлер
        cardView.setItemAnimator(new DefaultItemAnimator());
        cardView.setAdapter(recyclerAdapter); //установка адаптера
    }

    private void sendDialogDataToActivity(String name, String pin) { //функция считывания данных из диалога
        try {
            String sName = encrypt(name, "codeWord"); //шифрование данных по ключевому слову codeWord
            Toast.makeText(this, sName, Toast.LENGTH_SHORT).show(); //Тост с зашифрованными данными

            String sPin = encrypt(pin, "codeWord"); //шифрование данных по ключевому слову codeWord
            Toast.makeText(this, sPin, Toast.LENGTH_SHORT).show(); //Тост с зашифрованными данными

            pinManager.insertToDb(sName, sPin); //Запись в бд зашифрованных данных
            setAdapter(); //вызов метода загрузки адаптера
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String encrypt(String name, String codeWord) throws Exception{ //Функиця шифрования данных
        SecretKey key = generateKey(codeWord); //Генерация ключа по кодовому слову
        Cipher cipher = Cipher.getInstance("AES"); //Инициализация класса алгоритма шифрования с заданием системы защиты
        cipher.init(Cipher.ENCRYPT_MODE, key); //Инициализация функционала
        byte[] encValue = cipher.doFinal(name.getBytes()); //Проход по зашифрованным данным и дешифровка
        String encrypt = Base64.encodeToString(encValue, Base64.DEFAULT); //байтовый массив дл яхранения результата
        return encrypt;
    }

    private SecretKey generateKey(String codeWord) throws Exception{//Функция генерации ключа для расшифровки по кодовому слову
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = codeWord.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    @Override
    protected void onResume() { //Переопределенный метод, вызываемый при создании активити
        super.onResume();
        pinManager.openDb(); //Открытие бд
        setAdapter();//Загрузка данных в ресайклер
    }

    @Override
    protected void onDestroy() { //Переопределенный метод, вызываемый при уничтожении активити
        super.onDestroy();
        pinManager.closeDb(); //Закрытие бд
    }

}