package com.example.miproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        new Handler ().postDelayed (new Runnable () {
            @Override
            public void run() {
                readUserPreferences (getApplicationContext ());
            }
        }, 3000);
    }

    private void readUserPreferences(Context context ) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);



        if(status){
            Intent intent = new Intent(this, ListProductsActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public String rol (Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        String rol = sharedPref.getString("rol",null);
        return rol;
    }

}



