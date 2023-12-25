package com.example.lab7;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import data.TableHelper;
import data.addwork;

public class MainActivityAdd extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDescription;
    private Spinner spinnerClassification;
    private EditText editTextCalories;
    private Button buttonAdd;
    private Button buttonBack;

    // Переменные для сохранения данных пользователя
    private String enteredName;
    private String enteredDescription;
    private String selectedClassification;
    private String enteredCalories;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add);
        Bundle extras = getIntent().getExtras();

        // Установка русской локали для клавиатуры
        setRussianLocale();

        // Инициализация компонентов
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerClassification = findViewById(R.id.spinnerClasifik);
        editTextCalories = findViewById(R.id.editTextTextCollorirn);
        buttonAdd = findViewById(R.id.buttonAddBass);
        buttonBack = findViewById(R.id.buttonback);

        // Заполнение выпадающего списка данными из ресурсов
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_data,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassification.setAdapter(adapter);

        // Установка слушателя для обработки выбора элемента в выпадающем списке
        spinnerClassification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Обработка выбора элемента в выпадающем списке
                selectedClassification = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // Установка слушателя для кнопки "Добавить"
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение данных из полей
                enteredName = editTextName.getText().toString();
                enteredDescription = editTextDescription.getText().toString();
                enteredCalories = editTextCalories.getText().toString();

                // Проверка на пустоту
                if (enteredName.isEmpty() || enteredDescription.isEmpty() || enteredCalories.isEmpty()) {
                    Toast.makeText(MainActivityAdd.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Проверка на существование упражнения с таким именем в базе
                if (checkExerciseExists(enteredName)) {
                    Toast.makeText(MainActivityAdd.this, "Данное упражнение уже существует", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Вызываем метод getClassificationId для получения идентификатора классификации
                int classificationId = getClassificationId(selectedClassification);

                // Проверяем, что классификация найдена
                if (classificationId == -1) {
                    Toast.makeText(MainActivityAdd.this, "Неверная классификация", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Вызываем метод insertData для сохранения данных в базу
                insertData(enteredName, enteredDescription, classificationId, Integer.parseInt(enteredCalories));

                // Очистка полей после добавления данных
                editTextName.setText("");
                editTextDescription.setText("");
                spinnerClassification.setSelection(0);
                editTextCalories.setText("");

                Toast.makeText(MainActivityAdd.this, "Данные добавлены", Toast.LENGTH_SHORT).show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(MainActivityAdd.this, MainActivityListBasa.class);
                    startActivity(intent);

            }
        });
    }

    private void insertData(String name, String description, int classification, int calories) {
        // Получаем базу данных для записи
        TableHelper dbHelper = new TableHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Создаем объект ContentValues для удобства вставки данных
        ContentValues values = new ContentValues();
        values.put(addwork.GuestEntry.COLUMN_NAME, name);
        values.put(addwork.GuestEntry.COLUMN_DESCRIPTION, description);
        values.put(addwork.GuestEntry.COLUMN_TREINING, classification);
        values.put(addwork.GuestEntry.COLUMN_CALORIES, calories);

        // Вставляем данные и получаем ID новой записи
        long newRowId = db.insert(addwork.GuestEntry.TABLE_NAME, null, values);

        // Закрываем базу данных
        db.close();
    }

    private int getClassificationId(String classification) {
        // Предполагается, что у вас есть массив классификаций
        String[] classifications = getResources().getStringArray(R.array.spinner_data);

        // Ищем индекс в массиве
        for (int i = 0; i < classifications.length; i++) {
            if (classifications[i].equals(classification)) {
                return i;
            }
        }

        // Возвращаем -1, если классификация не найдена
        return -1;
    }

    private boolean checkExerciseExists(String exerciseName) {
        TableHelper dbHelper = new TableHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Определение столбцов, которые необходимо получить
        String[] projection = {addwork.GuestEntry.COLUMN_NAME};

        // Условие для выборки - упражнение с таким именем
        String selection = addwork.GuestEntry.COLUMN_NAME + "=?";
        String[] selectionArgs = {exerciseName};

        Cursor cursor = db.query(
                addwork.GuestEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Если курсор не пустой, значит, упражнение с таким именем уже существует
        boolean exerciseExists = cursor.moveToFirst();

        // Закрываем курсор и базу данных
        cursor.close();
        db.close();

        return exerciseExists;
    }

    private void setRussianLocale() {
        // Устанавливаем локаль на русскую
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);

        // Обновляем конфигурацию приложения
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
