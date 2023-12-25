package com.example.lab7;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.TableHelper;

public class MainActivityWorkout extends AppCompatActivity {

    private TextInputEditText editTextExerciseName;
    private TextInputEditText editTextExerciseDescription;
    private Chronometer chronometer;
    private ImageButton buttonPlay;
    private ImageButton buttonStop;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 60 секунд по умолчанию
    private boolean timerRunning;

    private int totalExercises;  // Измененный параметр
    private int currentExerciseIndex;
    private List<String> exerciseList;
    private TableHelper tableHelper;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_workout);

        editTextExerciseName = findViewById(R.id.editTextExerciseName);
        editTextExerciseDescription = findViewById(R.id.editTextExerciseDescription);
        chronometer = findViewById(R.id.chronometer);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonStop = findViewById(R.id.buttonStop);

        // Инициализация TableHelper
        tableHelper = new TableHelper(this);

        // Получение данных из другой активности
        Intent intent = getIntent();
        if (intent != null) {
            int workoutType = intent.getIntExtra("workoutType", 0);
            int totalExercises = intent.getIntExtra("totalExercises", 1);

            // Генерация упражнений в соответствии с переданным workoutType
            exerciseList = generateExercises(workoutType, totalExercises);
            currentExerciseIndex = 0;

            // Показать первое упражнение
            showNextExercise();
        }

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        if (!timerRunning) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    updateButtons();
                    showNextExercise();
                    timeLeftInMillis = 60000; // Возвращаем таймер обратно к 60 секундам
                    updateCountDownText();

                    // Воспроизводим звуковой сигнал при завершении таймера
                    playTimerFinishedSound();
                }
            }.start();

            timerRunning = true;
            updateButtons();
        }
    }

    // Метод для воспроизведения звукового сигнала при завершении таймера
    private void playTimerFinishedSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.timer_finished);
        }

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Обновленный метод stopTimer
    private void stopTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
            updateButtons();

            // Остановить воспроизведение звука при остановке таймера
            stopTimerFinishedSound();
        }
    }

    // Метод для остановки воспроизведения звукового сигнала
    private void stopTimerFinishedSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Обновленный метод onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Освобождаем ресурсы MediaPlayer при уничтожении активности
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        chronometer.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (timerRunning) {
            buttonPlay.setEnabled(false);
            buttonPlay.setColorFilter(getResources().getColor(android.R.color.darker_gray));
            buttonStop.setEnabled(true);
            buttonStop.setColorFilter(null);
        } else {
            buttonPlay.setEnabled(true);
            buttonPlay.setColorFilter(null);
            buttonStop.setEnabled(false);
            buttonStop.setColorFilter(getResources().getColor(android.R.color.darker_gray));
        }
    }

    private void showNextExercise() {
        if (currentExerciseIndex < exerciseList.size()) {
            String exerciseName = exerciseList.get(currentExerciseIndex);
            String exerciseDescription = tableHelper.getExerciseDescription(exerciseName);

            editTextExerciseName.setText(exerciseName);
            editTextExerciseDescription.setText(exerciseDescription);

            // Устанавливайте время упражнения в миллисекундах, например, 30 секунд
            timeLeftInMillis = 60000;
            updateCountDownText();

            currentExerciseIndex++;
        } else {
            // Все упражнения завершены
            // Показать AlertDialog с сообщением и кнопкой для возвращения на главный экран
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Поздравляю!")
                    .setMessage("Вы успешно завершили тренировку.")
                    .setPositiveButton("Вернуться на главный экран", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(MainActivityWorkout.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            // Скрыть остальные элементы
            editTextExerciseName.setVisibility(View.GONE);
            editTextExerciseDescription.setVisibility(View.GONE);
            chronometer.setVisibility(View.GONE);
            buttonPlay.setVisibility(View.GONE);
            buttonStop.setVisibility(View.GONE);
        }
    }

    private List<String> generateExercises(int workoutType, int totalExercises) {
        List<String> generatedExercises = new ArrayList<>();

        // Генерация упражнений в соответствии с workoutType
        switch (workoutType) {
            case 1:
                generatedExercises.addAll(tableHelper.getAvailableExercises(1, totalExercises));
                break;
            case 2:
                generatedExercises.addAll(tableHelper.getAvailableExercises(2, totalExercises));
                break;
            case 3:
                generatedExercises.addAll(tableHelper.getAvailableExercises(3, totalExercises));
                break;
            default:
                generatedExercises.addAll(tableHelper.getAvailableExercises(0, totalExercises)); // По умолчанию для workoutType = 0
                break;
        }

        return generatedExercises;
    }
}