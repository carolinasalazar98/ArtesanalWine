package com.example.miproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.miproyecto.entities.PreferenceEntity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        TimerTask tarea = new TimerTask () {
            @Override
            public void run() {
                Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity (intent);
                finish ();
            }
        };
        Timer tiempo = new Timer ();
        tiempo.schedule (tarea, 3000);

    }
    String email= PreferenceEntity.leer (1).toString ();
    boolean status = PreferenceEntity.leer (4).equals ("true");
}