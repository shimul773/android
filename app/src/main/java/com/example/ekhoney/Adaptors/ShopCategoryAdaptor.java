package com.example.ekhoney.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ekhoney.Modals.ShopCategoryModal;
import com.example.ekhoney.R;

import java.util.ArrayList;

public class ShopCategoryAdaptor extends ArrayAdapter<ShopCategoryModal> {

    public ShopCategoryAdaptor(@NonNull Context context, ArrayList<ShopCategoryModal> shopCategoryModalArrayList) {
        super(context, 0, shopCategoryModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    public View initView(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_spinner_item,parent, false);

        TextView spinner_text = convertView.findViewById(R.id.spinner_text);
        ShopCategoryModal shopCategoryModal = getItem(position);

        if(shopCategoryModal != null){
            spinner_text.setText(shopCategoryModal.getTitle() + " (" + shopCategoryModal.getProducts() + ")");
        }


        return convertView;
    }
}
