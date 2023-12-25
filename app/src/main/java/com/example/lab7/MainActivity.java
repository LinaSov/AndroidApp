package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupWorkoutType;
    private EditText editTextDuration;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBasa = findViewById(R.id.buttonBass);
        Button buttonCreate = findViewById(R.id.buttonNext);

        buttonBasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityListBasa.class);
                startActivity(intent);
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenerateWorkoutDialog();
            }
        });
    }

    private void showGenerateWorkoutDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_main_create);
        dialog.setTitle(R.string.random_name);

        // Изменение ширины диалогового окна
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        radioGroupWorkoutType = dialog.findViewById(R.id.radioGroupWorkoutType);
        editTextDuration = dialog.findViewById(R.id.editTextDuration);

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

        return true;
    }

    private void generateWorkout() {
        int selectedRadioButtonIndex = -1;

        for (int i = 0; i < radioGroupWorkoutType.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroupWorkoutType.getChildAt(i);
            if (radioButton.isChecked()) {
                selectedRadioButtonIndex = i;
                break;
            }
        }

        if (selectedRadioButtonIndex != -1) {
            // Получение введенной длительности тренировки
            String durationString = editTextDuration.getText().toString();
            Log.d("MainActivity", "Selected RadioButton Index: " + selectedRadioButtonIndex);
            // Передача данных в другую активность
            Intent intent = new Intent(MainActivity.this, MainActivityWorkout.class);
            intent.putExtra("workoutType", selectedRadioButtonIndex);
            intent.putExtra("totalExercises", Integer.parseInt(durationString));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Выберите тип тренировки", Toast.LENGTH_SHORT).show();
        }
    }

}

