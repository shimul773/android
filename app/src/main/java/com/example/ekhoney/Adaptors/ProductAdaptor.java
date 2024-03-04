package com.example.ekhoney.Adaptors;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ekhoney.Fragments.Product_details;
import com.example.ekhoney.Modals.ProductsModal;
import com.example.ekhoney.R;

import java.util.ArrayList;

public class ProductAdaptor extends RecyclerView.Adapter<ProductAdaptor.ProductViewHolder> {

    Context context;
    ArrayList<ProductsModal> productsModalArrayList;

    private OnProductImgclicklistener onProductImgclicklistener;
    private OnPlusBTNclicklistner onPlusBTNclicklistner;

    private OnMinusBTNclicklistner onMinusBTNclicklistner;

    public interface OnMinusBTNclicklistner{
        void OnMinusBTNclic(int position);
    }

    public void setOnMinusBTNclicklistner(OnMinusBTNclicklistner onMinusBTNclicklistner) {
        this.onMinusBTNclicklistner = onMinusBTNclicklistner;
    }

    public interface OnPlusBTNclicklistner{
        void OnPlusBTNclick(int position);
    }

    public void setOnPlusBTNclicklistner(OnPlusBTNclicklistner onPlusBTNclicklistner) {
        this.onPlusBTNclicklistner = onPlusBTNclicklistner;
    }

    public interface OnProductImgclicklistener{
        void OnProductImgclick(int position);
    }

    public void setOnProductImgclicklistener(OnProductImgclicklistener onProductImgclicklistener) {
        this.onProductImgclicklistener = onProductImgclicklistener;
    }

    public ProductAdaptor(Context context, ArrayList<ProductsModal> productsModalArrayList) {
        this.context = context;
        this.productsModalArrayList = productsModalArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.product_title.setText(productsModalArrayList.get(position).getTitle());
        holder.product_brand.setText("(" + productsModalArrayList.get(position).getBrand() + ")");
        holder.cart_quantity.setText(productsModalArrayList.get(position).getCart());
        Glide.with(context).load(productsModalArrayList.get(position).getImg()).into(holder.product_img);

        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onProductImgclicklistener != null){
                    onProductImgclicklistener.OnProductImgclick(position);
                }
            }
        });

        holder.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int currentQuantity = Integer.parseInt(holder.cart_quantity.getText().toString());
                //holder.cart_quantity.setText(String.valueOf(currentQuantity + 1));
                int currentqty = Integer.parseInt(holder.cart_quantity.getText().toString());
                holder.cart_quantity.setText(String.valueOf(currentqty + 1));
                if(onPlusBTNclicklistner != null){
                    onPlusBTNclicklistner.OnPlusBTNclick(position);
                }
            }
        });

        holder.minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentqty = Integer.parseInt(holder.cart_quantity.getText().toString());
                if(currentqty > 0){
                    int increasedqty = currentqty - 1;
                    holder.cart_quantity.setText(String.valueOf(increasedqty));
                }
                if(onMinusBTNclicklistner != null){
                    onMinusBTNclicklistner.OnMinusBTNclic(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsModalArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView product_title, product_price, product_mrp, product_brand, cart_quantity, plusbtn, minusbtn;
        ImageView product_img;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_title = itemView.findViewById(R.id.product_title);
            product_img = itemView.findViewById(R.id.product_img);
            product_brand = itemView.findViewById(R.id.product_brand);
            cart_quantity = itemView.findViewById(R.id.cart_quantity);
            plusbtn = itemView.findViewById(R.id.plusbtn);
            minusbtn = itemView.findViewById(R.id.minubtn);

        }
    }
}
