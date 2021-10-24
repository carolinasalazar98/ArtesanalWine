package com.example.miproyecto.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miproyecto.ListProductsActivity;
import com.example.miproyecto.R;
import com.example.miproyecto.databinding.ActivityEditProductsBinding;
import com.example.miproyecto.databinding.ActivityListProductsBinding;
import com.example.miproyecto.entities.ProductEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProductsActivity extends AppCompatActivity  implements View.OnClickListener{

    private ActivityEditProductsBinding editProductsBinding;
    private ProductEntity product;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        editProductsBinding= ActivityEditProductsBinding.inflate (getLayoutInflater ());
        View view = editProductsBinding.getRoot ();
        setContentView (view);
        editProductsBinding.btnUpdate.setOnClickListener (this);
        Intent intent = getIntent ();
        product= (ProductEntity) intent.getSerializableExtra ("product");
        db = FirebaseFirestore.getInstance ();
        editProductsBinding.etName.setText (product.getName ());
        editProductsBinding.etPrice.setText (String.valueOf (product.getPrice()));
        editProductsBinding.etStock.setText (String.valueOf (product.getStock ()));
        editProductsBinding.etDescription.setText (product.getDescription ());
    }


    @Override
    public void onClick(View v) {
        Map<String, Object> dataProduct = new HashMap<> ();
        dataProduct.put("name", editProductsBinding.etName.getText ().toString ());
        dataProduct.put("Price", Double.parseDouble (editProductsBinding.etPrice.getText ().toString ()));
        dataProduct.put("stock", Integer.parseInt (editProductsBinding.etStock.getText ().toString ()));
        dataProduct.put("description", editProductsBinding.etDescription.getText ().toString ());
        if(v.getId() == editProductsBinding.btnUpdate.getId()){
            db.collection ("products")
                    .document (product.getId ())
                    .update (dataProduct)
                    .addOnSuccessListener (aVoid -> {
                        Toast.makeText (getApplicationContext (),
                                "product updated", Toast.LENGTH_LONG).show ();
                        Intent intent = new Intent (getApplicationContext (), ListProductsActivity.class);
                        startActivity (intent);
                        finish ();
                    })
                    .addOnFailureListener (e -> Toast.makeText (getApplicationContext (), "Error updating product", Toast.LENGTH_LONG).show ());
        }
    }
}