package com.example.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityListBasa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_basa);

        ImageButton buttonCardio = findViewById(R.id.buttonCardio);
        ImageButton buttonsSrong = findViewById(R.id.buttonsSrong);
        ImageButton buttonBalet = findViewById(R.id.buttonBalet);
        ImageButton buttonStreching = findViewById(R.id.buttonStreching);
        Button add = findViewById(R.id.buttonAddList);
        Button back = findViewById(R.id.buttonBackList);

        buttonCardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityAdd(0);
            }
        });

        buttonsSrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityAdd(1);
            }
        });

        buttonBalet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityAdd(3);
            }
        });

        buttonStreching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityAdd(2);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityListBasa.this, MainActivityAdd.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityListBasa.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openMainActivityAdd(int value) {
        Intent intent = new Intent(MainActivityListBasa.this, MainActivityBasa.class);
        intent.putExtra("classification_value", value);
        startActivity(intent);
    }
}
