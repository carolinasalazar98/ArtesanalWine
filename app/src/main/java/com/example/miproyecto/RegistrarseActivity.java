package com.example.miproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistrarseActivity extends AppCompatActivity {
    /*private static final Pattern PASSWORD_PATTERN =
            Pattern.compile ("^" +
                    //"(?=.*[0-9])" +
                    //"(?=.*[a-z])" +
                    //"(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{8,}" +
                    "$");*/


    Spinner spinner1;
    private EditText jetnombre,jetemail,jetpais, jetciudad, jetclave,jetnomtienda;
    Button jbtnregistrar, jbtnregresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registrarse);

        jetnombre=findViewById(R.id.etnombre);
        jetemail=findViewById (R.id.etemail);
        jetpais=findViewById (R.id.etpais);
        jetciudad=findViewById (R.id.etciudad);
        jetclave=findViewById (R.id.etclave);
        jetnomtienda=findViewById (R.id.etnomtienda);
        jbtnregistrar=findViewById (R.id.btnregistrar);
        jbtnregresar=findViewById (R.id.btnregresar);
        spinner1=findViewById (R.id.spinner);

        String [] opciones={"Rol","°Usuario","°Vendedor"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item,opciones);
        spinner1.setAdapter (adapter);



    }
    private boolean validateNombre(){
        String nombre = jetnombre.getText ().toString ().trim ();

        if(nombre.isEmpty ()){
            jetnombre.setError ("Nombre Requerido");
            return false;
        }else{
            jetnombre.setError (null);
            return true;
        }
    }
    private boolean validateEmail() {

        String email = jetemail .getText ().toString ().trim ();

        if (email.isEmpty ()) {
            jetemail.setError ("El campo no puede estar vacio");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher (email).matches ()) {
            jetemail.setError ("Ingrese una dirección de correo electrónico válida");
            return false;
        } else {
            jetemail.setError (null);
            return true;
        }
    }
    private boolean validatepais(){
        String nombre = jetpais.getText ().toString ().trim ();

        if(nombre.isEmpty ()){
            jetpais.setError ("País Requerido");
            return false;
        }else{
            jetpais.setError (null);
            return true;
        }
    }
    private boolean validateciudad(){
        String nombre = jetciudad.getText ().toString ().trim ();

        if(nombre.isEmpty ()){
            jetciudad.setError ("Ciudad Requerido");
            return false;
        }else{
            jetciudad.setError (null);
            return true;
        }
    }
    private boolean validatePassword() {
        String password = jetclave.getText ().toString ().trim ();

        if (password.isEmpty ()) {
            jetclave.setError ("El campo no puede estar vacio");
            return false;
        } else if (!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])(?=\\w*[@#$%^&+=])\\S{6,20}$", password)) {
            jetclave.setError ("Contraseña muy debíl");
            return false;
        }else{
            jetclave.setError (null);
            return true;
        }
    }

    private boolean validatenomtienda(){
        String nombre = jetnomtienda.getText ().toString ().trim ();

        if(nombre.isEmpty ()){
            jetnomtienda.setError ("Nombre Tienda Requerido");
            return false;
        }else{
            jetnomtienda.setError (null);
            return true;
        }
    }
    public void Registrarse(View view){
        if(!validateNombre ()| !validateEmail () | !validatepais ()| !validateciudad ()| !validatePassword ()| !validatenomtienda ()){
            return;
        }else{
            Intent intmostrar=new Intent(this,MostrarActivity.class);
            startActivity(intmostrar);
        }
    }
    public void Regresar(View view){
        Intent intlogin=new Intent(this,LoginActivity.class);
        startActivity(intlogin);
    }

}