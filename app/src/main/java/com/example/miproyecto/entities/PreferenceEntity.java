package com.example.miproyecto.entities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.miproyecto.R;

public class PreferenceEntity {

    private static Context context;
    private  static String email;
    private static  String name;
    private static String rol;
    private  static boolean status;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        PreferenceEntity.context = context;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        PreferenceEntity.email = email;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        PreferenceEntity.name = name;
    }

    public static String getRol() {
        return rol;
    }

    public static void setRol(String rol) {
        PreferenceEntity.rol = rol;
    }

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        PreferenceEntity.status = status;
    }

    public  static  void SavePreferences(){
        SharedPreferences preferences = getContext ().getSharedPreferences (getContext ()
                .getText (R.string.user_preference_key).toString (),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit ();
        if (!getEmail ().isEmpty ()) {
            editor.putString ("email",getEmail ());
        }
        if (!getName ().isEmpty ()) {
            editor.putString ("name",getName ());
        }
        if (!getRol ().isEmpty ()) {
            editor.putString ("rol",getRol ());
        }
        if(isStatus ()){
            editor.putBoolean ("status",isStatus ());
        }
        editor.commit ();
    }
    public static Object leer(int item){
        SharedPreferences preferences = getContext ().getSharedPreferences (getContext ()
                .getText (R.string.user_preference_key).toString (),Context.MODE_PRIVATE);
        switch (item){
            case 1:
                PreferenceEntity.email = preferences.getString ("email","");
                return getEmail ();
            case 2:
                PreferenceEntity.name = preferences.getString ("name", "");
                return getName ();
            case 3:
                PreferenceEntity.rol = preferences.getString ("rol", "");
                return  getRol ();
            case 4:
                PreferenceEntity.status = preferences.getBoolean ("status", false);
                return isStatus ();
            default:
                return "";
        }
    }
    public  static  void limpiar(){
        SharedPreferences preferences = getContext ().getSharedPreferences (getContext ()
                .getText (R.string.user_preference_key).toString (),Context.MODE_PRIVATE);
        preferences.edit ().clear ().commit ();
    }
}
