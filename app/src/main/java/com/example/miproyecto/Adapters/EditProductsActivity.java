package com.example.miproyecto.Adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.miproyecto.R;
import com.example.miproyecto.databinding.ActivityEditProductsBinding;
import com.example.miproyecto.databinding.ActivityListProductsBinding;
import com.example.miproyecto.entities.ProductEntity;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProductsActivity extends AppCompatActivity {

    private ActivityEditProductsBinding editProductsBinding;
    private ProductEntity product;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        editProductsBinding= ActivityEditProductsBinding.inflate (getLayoutInflater ());
        View view = editProductsBinding.getRoot ();
        setContentView (view);
        Intent intent = getIntent ();
        product= (ProductEntity) intent.getSerializableExtra ("product");
        db = FirebaseFirestore.getInstance ();
    }
    public void updateProduct(View view ){

    }

}