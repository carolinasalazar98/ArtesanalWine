package com.example.miproyecto;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miproyecto.entities.SharedEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    EditText etemail, etpassword;
    SharedEntity users;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance ();
        users = new SharedEntity ();
        db = FirebaseFirestore.getInstance ();
        etemail = findViewById (R.id.etemail);
        etpassword = findViewById (R.id.etpassword);

    }


    public void login(View view) {
        String email = etemail.getText ().toString ();
        String password = etpassword.getText ().toString ();

        if (email.isEmpty ()) {
            etemail.setError ("El campo no puede estar vacio");
        } else if (!Patterns.EMAIL_ADDRESS.matcher (email).matches ()) {
            etemail.setError ("Ingrese una dirección de correo electrónico válida");

        } else if (password.isEmpty ()) {
            etpassword.setError ("El campo no puede estar vacio");

        } else if (!Pattern.matches ("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])(?=\\w*[@#$%^&+=])\\S{6,20}$", password)) {
            etpassword.setError ("Contraseña muy debíl");

        } else {
            mAuth.signInWithEmailAndPassword (email, password)
                    .addOnCompleteListener (this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful ()) {
                                db.collection ("artesanalwine").whereEqualTo ("email", email)
                                        .get ()
                                        .addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful ()) {
                                                    for (QueryDocumentSnapshot documentSnapshots : task.getResult ()) {
                                                        users = documentSnapshots.toObject (SharedEntity.class);
                                                        break;
                                                    }
                                                    Toast.makeText (getApplicationContext (),
                                                            "Login exitoso", Toast.LENGTH_SHORT).show ();
                                                    saveUserPreferences (getApplicationContext ());
                                                    Intent intent = new Intent (getApplicationContext (), ListProductsActivity.class);
                                                    startActivity (intent);
                                                }
                                            }
                                        });
                            } else {
                                Log.w (TAG, "signInWithEmail:failure", task.getException ());
                                Toast.makeText (getApplicationContext (), "Login Fallido", Toast.LENGTH_SHORT).show ();
                            }
                        }

                        private void saveUserPreferences(Context context) {
                            SharedPreferences sharedPref = context.getSharedPreferences (
                                    getString (R.string.user_preference_key), Context.MODE_PRIVATE);
                            // permite escribir data en las shared preferences
                            SharedPreferences.Editor editor = sharedPref.edit ();
                            editor.putBoolean ("status", true);
                            editor.putString ("email", users.getEmail ());
                            editor.putString ("rol", users.getRol ());
                            editor.putString ("name", users.getName ());
                            editor.commit ();
                        }

                    });
        }

    }

    public void Registrarse(View view) {
        Intent intregistrarse = new Intent (this, RegistrarseActivity.class);
        startActivity (intregistrarse);
    }
}












