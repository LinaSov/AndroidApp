package com.example.lab7;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityCreate extends AppCompatActivity {

    private RadioGroup radioGroupWorkoutType;
    private EditText editTextDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create);

        radioGroupWorkoutType = findViewById(R.id.radioGroupWorkoutType);
        editTextDuration = findViewById(R.id.editTextDuration);

        Button buttonGenerate = findViewById(R.id.buttonGenerate);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenerateWorkoutDialog();
            }
        });
    }

    private void showGenerateWorkoutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_main_create);
        dialog.setTitle(R.string.random_name);

        // Изменение ширины диалогового окна
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        final RadioGroup radioGroupWorkoutType = dialog.findViewById(R.id.radioGroupWorkoutType);
        final EditText editTextDuration = dialog.findViewById(R.id.editTextDuration);

        Button buttonGenerate = dialog.findViewById(R.id.buttonGenerate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields(editTextDuration, radioGroupWorkoutType)) {
                    generateWorkout();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private boolean validateFields(EditText editTextDuration, RadioGroup radioGroupWorkoutType) {
        // Проверка наличия выбранного типа тренировки
        if (radioGroupWorkoutType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Выберите тип тренировки", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка наличия введенной длительности тренировки
        String durationString = editTextDuration.getText().toString();
        if (TextUtils.isEmpty(durationString)) {
            Toast.makeText(this, "Введите длительность тренировки", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка, что длительность является целым числом
        try {
            Integer.parseInt(durationString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Длительность тренировки должна быть целым числом", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка на четность введенной длительности
        int duration = Integer.parseInt(durationString);
        if (duration % 2 != 0) {
            Toast.makeText(this, "Длительность тренировки должна быть четным числом", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void generateWorkout() {
        // Получение выбранного типа тренировки
        int selectedWorkoutTypeId = radioGroupWorkoutType.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedWorkoutTypeId);
        String workoutType = selectedRadioButton.getText().toString();

        // Получение введенной длительности тренировки
        String durationString = editTextDuration.getText().toString();

        // Передача данных в другую активность
        Intent intent = new Intent(MainActivityCreate.this, MainActivityWorkout.class);
        intent.putExtra("workoutType", workoutType);
        intent.putExtra("duration", Integer.parseInt(durationString));
        startActivity(intent);
    }
}
