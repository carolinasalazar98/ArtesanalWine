package com.example.miproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Pattern;

public class RegistrarseActivity extends AppCompatActivity {

    Spinner spinner1;
    private EditText jetnombre,jetemail,jetpais, jetciudad, jetclave,jetnomtienda;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registrarse);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        jetnombre=findViewById(R.id.etnombre);
        jetemail=findViewById (R.id.etemail);
        jetpais=findViewById (R.id.etpais);
        jetciudad=findViewById (R.id.etciudad);
        jetclave=findViewById (R.id.etclave);
        jetnomtienda=findViewById (R.id.etnomtienda);

        spinner1=findViewById (R.id.spinner);

        String [] opciones={"Rol","°Usuario","°Vendedor"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item,opciones);
        spinner1.setAdapter (adapter);



    }


    public void register(View View){
        String nombre = jetnombre.getText ().toString ();
        String email = jetemail .getText ().toString ();
        String pais = jetpais .getText ().toString();
        String ciudad = jetciudad .getText ().toString ();
        String password = jetclave.getText ().toString ();
        String nomtienda = jetnomtienda .getText ().toString ();

        if(nombre.isEmpty ()){
            jetnombre.setError ("Nombre Requerido");

        }else if(email.isEmpty ()) {
            jetemail.setError ("El campo no puede estar vacio");

        } else if (!Patterns.EMAIL_ADDRESS.matcher (email).matches ()) {
            jetemail.setError ("Ingrese una dirección de correo electrónico válida");

        } else if(pais.isEmpty ()){
            jetpais.setError ("País Requerido");

        }else if(ciudad.isEmpty ()){
            jetciudad.setError ("Ciudad Requerido");

        }else  if (password.isEmpty ()) {
            jetclave.setError ("El campo no puede estar vacio");

        } else if (!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])(?=\\w*[@#$%^&+=])\\S{6,20}$", password)) {
            jetclave.setError ("Contraseña muy debíl");

        }else  if(nomtienda.isEmpty ()){
            jetnomtienda.setError ("Nombre Tienda Requerido");

        }else{
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
                                Toast.makeText(getApplicationContext(), "Fallido:", Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void saveUserToFirestore() {
                            Map<String, Object> user = new HashMap<> ();
                            String email = jetemail.getText().toString();
                            user.put("name", nombre);
                            user.put("email", email);
                            user.put("pais", pais);
                            user.put("ciudad", ciudad);
                            user.put("clave", password);
                            user.put("tienda", nomtienda);
                            db.collection("artesanalwine")
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
                                                    "Fallido", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
        }
    }


    public void regresar(View view){
        Intent intlogin=new Intent(this,LoginActivity.class);
        startActivity(intlogin);
    }

}