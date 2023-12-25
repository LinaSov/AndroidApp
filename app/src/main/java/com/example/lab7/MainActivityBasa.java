package com.example.lab7;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import data.TableHelper;
import data.addwork;

public class MainActivityBasa extends AppCompatActivity {

    private TextView textView;
    private int selectedTraining;
    private int selectedTrainingAdd;
    private TableHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_basa);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedTraining = extras.getInt("classification_value", 0);
        }

        textView = findViewById(R.id.textViewData);
        ImageButton buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityBasa.this, MainActivityAdd.class);
                startActivity(intent);
            }
        });
        Button buttonbeck = findViewById(R.id.buttonBackBasa);
        buttonbeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityBasa.this, MainActivityListBasa.class);
                startActivity(intent);
            }
        });
        dbHelper = new TableHelper(this);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Создаем и открываем базу данных для чтения
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Определяем столбцы, которые хотим извлечь
        String[] projection = {
                addwork.GuestEntry._ID,
                addwork.GuestEntry.COLUMN_NAME,
                addwork.GuestEntry.COLUMN_TREINING,
                addwork.GuestEntry.COLUMN_DESCRIPTION,
                addwork.GuestEntry.COLUMN_CALORIES
        };

        // Выбираем записи только с выбранной тренировкой
        String selection = addwork.GuestEntry.COLUMN_TREINING + "=?";
        String[] selectionArgs = {String.valueOf(selectedTraining)};

        // Получаем курсор, который будет перебирать результаты запроса
        Cursor cursor = db.query(
                addwork.GuestEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        try {
            // Определяем индексы столбцов
            int idColumnIndex = cursor.getColumnIndex(addwork.GuestEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(addwork.GuestEntry.COLUMN_NAME);
            int trainingColumnIndex = cursor.getColumnIndex(addwork.GuestEntry.COLUMN_TREINING);
            int caloriesColumnIndex = cursor.getColumnIndex(addwork.GuestEntry.COLUMN_CALORIES);
            int descriptionColumnIndex = cursor.getColumnIndex(addwork.GuestEntry.COLUMN_DESCRIPTION);

            // Перебираем результаты и выводим только названия упражнений в TextView
            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentTraining = cursor.getInt(trainingColumnIndex);
                int currentCalories = cursor.getInt(caloriesColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);

                // Проверяем, соответствует ли запись условию выбранной тренировки
                if (currentTraining == selectedTraining) {
                    // Выводим данные в ваш TextView или используйте их по своему усмотрению
                    String data = "Название: " + currentName + "\n";
                    data += "Описание: " + currentDescription + "\n";
                    data += "Кол-во калорий в мин. : " + currentCalories + "\n\n";

                    textView.append(data);
                }
            }
        } finally {
            // Всегда закрываем курсор после использования
            cursor.close();
        }
        ImageButton imageButtonDelit = findViewById(R.id.imageButtonDelit);

        imageButtonDelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteExerciseDialog();
            }
        });
    }

    private void showDeleteExerciseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удаление упражнения");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String exerciseName = input.getText().toString().trim();
                deleteExercise(exerciseName);
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void deleteExercise(String exerciseName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Определяем условие WHERE для удаления конкретного упражнения
        String selection = addwork.GuestEntry.COLUMN_NAME + "=?";
        String[] selectionArgs = {exerciseName};

        // Удаляем упражнение
        int deletedRows = db.delete(addwork.GuestEntry.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            // Упражнение успешно удалено
            textView.setText(""); // Очищаем TextView перед обновлением данных
            displayDatabaseInfo();
            Toast.makeText(this, "Упражнение успешно удалено", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Упражнение не найдено", Toast.LENGTH_SHORT).show();
        }
    }
}
