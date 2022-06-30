package com.example.bankpinjava.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bankpinjava.Dialog.MyDialogFragment;
import com.example.bankpinjava.R;
import com.example.bankpinjava.authdb.AuthDbManager;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener {
    //Создание пременных объектов активити
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;
    private ImageButton deleteButton;
    private Button passButton;

    private EditText passwordText;
    private TextView introText;

    private AuthDbManager authDbManager; //Создание переменной менеджера
    private String password; //строка пароля
    private boolean passwordStatus; //строка статуса

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        init();
        initListener();

        authDbManager = new AuthDbManager(this);


    }

    @Override
    protected void onResume() { //Переопределенный метод, вызываемый при создании активити
        super.onResume();
        authDbManager.openDb(); //Открытие бд
        checkPassFromDb(); // Вызов метода проверки пароля из бд
    }

    @SuppressLint("WrongViewCast")
    private void init(){ //Подвязка переменных к объектам на активити
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        deleteButton = findViewById(R.id.deleteButton);
        passButton = findViewById(R.id.passButton);

        passwordText = findViewById(R.id.passwordText);
        introText = findViewById(R.id.introText);
    }

    private void initListener(){ //Инициализация слушателей
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);

        deleteButton.setOnClickListener(deleteButtonClickListener);
        passButton.setOnClickListener(passButtonClickListener);
    }

    View.OnClickListener deleteButtonClickListener = new View.OnClickListener() { //Слушатель кнопки удаления введенных значений
        @Override
        public void onClick(View v) {
            String deleteText = String.valueOf(passwordText.getText()); //считывание данных с поля пароля
            passwordText.setText(removeLastChar(deleteText)); //установка в поле пароля измененного значения
        }
    };

    View.OnClickListener passButtonClickListener = new View.OnClickListener() {  //Слушатель кнопки забытого пароля
        @Override
        public void onClick(View v) {
            checkPassFromDb(); // Вызов метода проверки пароля из бд
            //Создание алертдиалога и отправка в него пароля
            FragmentManager manager = getSupportFragmentManager();
            MyDialogFragment myDialogFragment = new MyDialogFragment(password);
            myDialogFragment.show(manager, "myDialog");
        }
    };


    public String removeLastChar(String s) { //Функция удаления последнего символа строки
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }

    @Override
    public void onClick(View view) { //Метод, привязанный к кнопкам ввода цифр
        int btnClicked = view.getId();
        switch (btnClicked){ //Проверка кнопки по id
            case R.id.one:{
                String text = passwordText.getText() + "1"; //Добавление в строку значения кнопки
                passwordText.setText(text);

            }break;
            case R.id.two:{
                String text = passwordText.getText() + "2";
                passwordText.setText(text);

            }break;
            case R.id.three:{
                String text = passwordText.getText() + "3";
                passwordText.setText(text);

            }break;
            case R.id.four:{
                String text = passwordText.getText() + "4";
                passwordText.setText(text);

            }break;
            case R.id.five:{
                String text = passwordText.getText() + "5";
                passwordText.setText(text);

            }break;
            case R.id.six:{
                String text = passwordText.getText() + "6";
                passwordText.setText(text);

            }break;
            case R.id.seven:{
                String text = passwordText.getText() + "7";
                passwordText.setText(text);

            }break;
            case R.id.eight:{
                String text = passwordText.getText() + "8";
                passwordText.setText(text);

            }break;
            case R.id.nine:{
                String text = passwordText.getText() + "9";
                passwordText.setText(text);

            }break;
            case R.id.zero:{
                String text = passwordText.getText() + "0";
                passwordText.setText(text);

            }break;
        }
        if(passwordStatus == false){ //Проверка сценария первого входа
            createPassword(); //Функция установки пароля при первом входе
        }
        else{
            checkPassword(); //Функция проверки пароля
        }
    }

    @SuppressLint("ResourceAsColor")
    private void checkPassword(){ //Функция проверки пароля
        if(passwordText.getText().length() == 4 && passwordText.getText().toString().equals(password)){ //Если длина поля ввода пароля равна 4 и пароль совпал
            passwordText.setText(""); //Очистка поля ввода пароля
            Intent intent = new Intent(this, MainActivity.class); //Создание интента для перехода в другую активити
            startActivity(intent); //Старт новой активити
        }
        else if(passwordText.getText().length() == 4 && !passwordText.getText().toString().equals(password)){ //Если длана  равна 4 и пароль не совпал
            passwordText.setTextColor(R.color.red);
            Handler handler = new Handler(); // Поток с задержкой
            handler.postDelayed(new Runnable() {
                public void run() {//Запуск в основном потоке

                    passwordText.setText(""); //Очистка текста
                    passwordText.setTextColor(R.color.white);
                }
            }, 1000); //Задержка в секунду
            Toast.makeText(getApplicationContext(), getText(R.string.error_password), Toast.LENGTH_SHORT).show(); //Тост с информацией о неверном пароле
        }
    }

    private void createPassword(){ //Функция ввода пароля при первом запуске
        if(passwordText.getText().length() == 4){ //Если длина равна 4 то
            authDbManager.insertToDb(passwordText.getText().toString()); //Запись пароля в бд
            passwordText.setText("");
            checkPassFromDb(); //Функция проверки пароля в бд
            introText.setText(getText(R.string.replay_password));//Замена текста на текст с просьбой ввода повторно пароля
        }
    }

    private void checkPassFromDb(){
        password = authDbManager.readFromDb(); //Считывание пароля из бд
        if (password.isEmpty()){ //Если результат пустой
            passwordStatus = false; //Статус пароля
            introText.setText(getText(R.string.create_password)); //Просьба ввода пароля
        }
        else{
            passwordStatus = true; //Статус пароля
            introText.setText(getText(R.string.password_text));
        }

    }

    @Override
    protected void onDestroy() { //Переопределенный метод, вызываемый при уничтожении активити
        super.onDestroy();
        authDbManager.closeDb(); //Закрытие бд
    }
}
