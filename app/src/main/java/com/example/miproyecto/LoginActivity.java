package com.example.miproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.miproyecto.Adapters.ProductAdapter;
import com.example.miproyecto.databinding.ActivityLoginBinding;
import com.example.miproyecto.databinding.ActivityMainBinding;
import com.example.miproyecto.entities.PreferenceEntity;
import com.example.miproyecto.entities.ProductEntity;
import com.example.miproyecto.entities.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    EditText etemail, etpassword;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private ArrayList<Usuario> arrayList;
    private  Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        PreferenceEntity.setContext (this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<> ();


        etemail = findViewById (R.id.etemail);
        etpassword = findViewById (R.id.etpassword);

    }


    public void login(View view) {
        String email = etemail.getText().toString();
        String password =  etpassword.getText().toString();

        if (email.isEmpty ()) {
            etemail.setError ("El campo no puede estar vacio");
        } else if (!Patterns.EMAIL_ADDRESS.matcher (email).matches ()) {
            etemail.setError ("Ingrese una dirección de correo electrónico válida");

        } else if(password.isEmpty ()) {
            etpassword.setError ("El campo no puede estar vacio");

        } else if (!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])(?=\\w*[@#$%^&+=])\\S{6,20}$", password)) {
            etpassword.setError ("Contraseña muy debíl");

        }else{
            mAuth.signInWithEmailAndPassword (email, password)
                    .addOnCompleteListener (this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful ()) {
                                consulta ();
                            } else {
                                Toast.makeText (getApplicationContext (), "Login Fallido", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });

        }
    }
    public void  consulta(){
        String email=etemail.getText ().toString ();
        db.collection ("artesanalwine").whereEqualTo ("email",email)
                .get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful ()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult ()){
                        usuario=documentSnapshot.toObject (Usuario.class);
                        break;
                    }
                    PreferenceEntity.setEmail (usuario.getEmail ());
                    PreferenceEntity.setName (usuario.getName ());
                    PreferenceEntity.setRol (usuario.getRol ());
                    PreferenceEntity.setStatus (true);

                    PreferenceEntity.SavePreferences ();

                    Toast.makeText (getApplicationContext (),
                            "Login exitoso", Toast.LENGTH_SHORT).show ();
                    Intent intent = new Intent (getApplicationContext (), ListProductsActivity.class);
                    startActivity(intent);
                }else{

                }
            }
        });
    }


    public void Registrarse(View view){
        Intent intregistrarse=new Intent(this,RegistrarseActivity.class);
        startActivity(intregistrarse);
    }
}


