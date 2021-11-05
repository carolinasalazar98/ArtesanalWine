package com.example.miproyecto;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miproyecto.databinding.ActivityAddProductBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity  {

    private Context context;
    private ActivityAddProductBinding addProductBinding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_product);
        addProductBinding = ActivityAddProductBinding.inflate (getLayoutInflater ());
        View view = addProductBinding.getRoot ();
        setContentView (view);
        mAuth = FirebaseAuth.getInstance ();
        db = FirebaseFirestore.getInstance ();


    }

    public void pickImage(View view) {
        Intent intent = new Intent (Intent.ACTION_PICK);
        intent.setType ("image/*");
        galleryActivityLauncher.launch (intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityLauncher = registerForActivityResult (
            new ActivityResultContracts.StartActivityForResult (),
            new ActivityResultCallback<ActivityResult> () {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode () == Activity.RESULT_OK) {
                        Intent data = result.getData (); // obtiene la data cuando selecciona la imagen
                        Uri imageUri = data.getData (); // obtiene la ruta de la imagen
                        if (imageUri != null) {
                            addProductBinding.ivProduct.setImageURI (imageUri);
                        }
                    } else {
                        Toast.makeText (getApplicationContext (), "No image selected", Toast.LENGTH_SHORT).show ();
                    }
                }
            }
    );

    public void agregar(View view) {
        String product = addProductBinding.etProductName.getText ().toString ();
        String price = addProductBinding.etPrice.getText ().toString ();
        String stock = addProductBinding.etStock.getText ().toString ();
        String categoty = addProductBinding.etCategory.getText ().toString ();
        String description = addProductBinding.etDescription.getText ().toString ();

        if (product.isEmpty () || price.isEmpty () || stock.isEmpty () || categoty.isEmpty () || description.isEmpty ()) {
            Toast.makeText (this, "campos requeridos", Toast.LENGTH_LONG).show ();
            addProductBinding.etProductName.requestFocus ();
        } else {

                Map<String, Object> dataProduct = new HashMap<> ();
                dataProduct.put ("name", product);
                dataProduct.put ("price", Double.parseDouble (price));
                dataProduct.put ("stock", Integer.parseInt (stock));
                dataProduct.put ("category", categoty);
                dataProduct.put ("description", description);
                db.collection ("products")
                        .add (dataProduct)
                        .addOnSuccessListener (new OnSuccessListener<DocumentReference> () {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText (getApplicationContext (),
                                        "Registro completo", Toast.LENGTH_SHORT).show ();
                            }
                        })
                        .addOnFailureListener (new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText (getApplicationContext (),
                                        "Fallido", Toast.LENGTH_SHORT).show ();
                            }
                        });
            }
        }
        }



