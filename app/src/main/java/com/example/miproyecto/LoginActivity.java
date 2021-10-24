package com.example.miproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miproyecto.Adapters.EditProductsActivity;
import com.example.miproyecto.databinding.ActivityListProductsBinding;
import com.example.miproyecto.entities.ProductEntity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
   /* private static final Pattern PASSWORD_PATTERN =
            Pattern.compile ("^" +
                    "(?=.*[0-9])" +
                    //"(?=.*[a-z])" +
                    //"(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6}" +
                    "$");*/

    private EditText etemail, etpassword;
    Button btningresar, btnregistrarse;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        etemail = findViewById (R.id.etemail);
        etpassword = findViewById (R.id.etpassword);
        btningresar = findViewById (R.id.btningresar);
        btnregistrarse = findViewById (R.id.btnregistrarse);

    }

    private boolean validateEmail() {
        String email = etemail.getText ().toString ().trim ();

        if (email.isEmpty ()) {
            etemail.setError ("El campo no puede estar vacio");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher (email).matches ()) {
            etemail.setError ("Ingrese una dirección de correo electrónico válida");
            return false;
        } else {
            etemail.setError (null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = etpassword.getText ().toString ().trim ();

        if (password.isEmpty ()) {
            etpassword.setError ("El campo no puede estar vacio");
            return false;
        } else if (!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])(?=\\w*[@#$%^&+=])\\S{6,20}$", password)) {
            etpassword.setError ("Contraseña muy debíl");
            return false;
        }else{
            etpassword.setError (null);
            return true;
        }
    }

    public void ingresar(View view){
        if(!validateEmail () | !validatePassword ()){
            return;
        }else{
            Intent intentlist=new Intent(this, ListProductsActivity.class);
            startActivity(intentlist);
        }
    }
    public void Registrarse(View view){
        Intent intregistrarse=new Intent(this,RegistrarseActivity.class);
        startActivity(intregistrarse);
    }
}

