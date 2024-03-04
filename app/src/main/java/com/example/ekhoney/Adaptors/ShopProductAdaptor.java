package com.example.ekhoney.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekhoney.Modals.ProductsModal;
import com.example.ekhoney.R;

import java.util.ArrayList;

public class ShopProductAdaptor extends RecyclerView.Adapter<ShopProductAdaptor.ShopProductViewHolder>{

    Context context;
    ArrayList<ProductsModal> productsModalArrayList;

    public ShopProductAdaptor(Context context, ArrayList<ProductsModal> productsModalArrayList) {
        this.context = context;
        this.productsModalArrayList = productsModalArrayList;
    }

    @NonNull
    @Override
    public ShopProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_shop_product, parent, false);
        return new ShopProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopProductViewHolder holder, int position) {

        holder.shop_product_title.setText(productsModalArrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return productsModalArrayList.size();
    }

    public class ShopProductViewHolder extends RecyclerView.ViewHolder{

        TextView shop_product_title;
        public ShopProductViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_product_title = itemView.findViewById(R.id.shop_product_title);
        }
    }
}
