package com.example.miproyecto.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miproyecto.databinding.ProductItemBinding;
import com.example.miproyecto.entities.ProductEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private ProductItemBinding productItemBinding;
    private ArrayList<ProductEntity> productsArrayList;
    private FirebaseFirestore db;

    public ProductAdapter(
            Context context,
            ArrayList<ProductEntity> productsArrayList,
            FirebaseFirestore db) {
        this.context = context;
        this.productsArrayList = productsArrayList;
        this.db = db;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productItemBinding = ProductItemBinding.inflate (LayoutInflater.from (context));
        return new ProductViewHolder (productItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductEntity product = productsArrayList.get (position);
        holder.itemBinding.tvProductName.setText (product.getName ());
        holder.itemBinding.tvDescription.setText (product.getDescription ());
        holder.itemBinding.tvStock.setText (String.valueOf (product.getStock ()));
        holder.itemBinding.tvCategory.setText (product.getCategory ());
        holder.itemBinding.tvPrice.setText (String.valueOf (product.getPrice ()));
        AlertDialog.Builder builder = new AlertDialog.Builder (context);
        builder.setPositiveButton ("Aceptar", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.collection ("products").document (product.getId ())
                        .delete ()
                        .addOnSuccessListener (new OnSuccessListener<Void> () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText (context, " Data Delete", Toast.LENGTH_LONG).show ();
                                productsArrayList.remove (holder.getAdapterPosition ());
                                notifyDataSetChanged ();
                            }
                        })
                        .addOnFailureListener (new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText (context, "failes to delete item",
                                        Toast.LENGTH_LONG).show ();
                            }
                        });
            }
        });
        builder.setNegativeButton ("Cancelar", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        holder.itemBinding.btnDelete.setOnClickListener (v -> {
        builder.setMessage ("¿Esta seguro que quiere eliminar el producto?");
        builder.create ().show ();
        });
        holder.itemBinding.btnEdit.setOnClickListener (v -> {
            Intent intent = new Intent (context, EditProductsActivity.class);
            intent.putExtra ("products", product);
            context.startActivity (intent);
        });
    }


    @Override
    public int getItemCount()
    {
        return productsArrayList.size ();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ProductItemBinding itemBinding;

        public ProductViewHolder(@NonNull ProductItemBinding ListProductsBinding) {
            super (ListProductsBinding.getRoot ());
            this.itemBinding = ListProductsBinding;
        }
    }
}
