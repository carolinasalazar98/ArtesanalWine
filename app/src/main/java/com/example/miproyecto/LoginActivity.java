package com.example.miproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miproyecto.Adapters.EditProductsActivity;
import com.example.miproyecto.databinding.ActivityListProductsBinding;
import com.example.miproyecto.databinding.ActivityLoginBinding;
import com.example.miproyecto.databinding.ActivityMainBinding;
import com.example.miproyecto.entities.ProductEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
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


    EditText etemail, etpassword;
    Button btningresar, btnregistrarse;
    //private ActivityLoginBinding activityLoginBinding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        //ActivityLoginBinding = activityLoginBinding.inflate(getLayoutInflater());
        //View view = ActivityLoginBinding.getRoot();
        //setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etemail = findViewById (R.id.etemail);
        etpassword = findViewById (R.id.etpassword);
        btningresar = findViewById (R.id.btningresar);
        btnregistrarse = findViewById (R.id.btnregistrarse);

    }

    public void register(View View){
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            saveUserToFirestore();
                            Toast.makeText(getApplicationContext(),
                                    "Registro exitoso", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.d("Error:", "" + task.getException());
                            Toast.makeText(getApplicationContext(), "Sea Serio:", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void login(View view){
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Login exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ListProductsActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Sea Serio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToFirestore() {
        Map<String, Object> user = new HashMap<> ();
        String email = etemail.getText().toString();
        user.put("email", email);
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
        db.collection("pejelagartoShop")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference> () {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),
                                "Registro completo", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Sea serio", Toast.LENGTH_SHORT).show();
                    }
                });
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


