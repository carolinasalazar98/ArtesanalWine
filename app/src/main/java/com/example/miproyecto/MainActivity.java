package com.example.miproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
    public void readUserPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);
        String role = sharedPref.getString("role","usuario");
        if(status){
            Intent intent = new Intent(this, ListProductsActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}