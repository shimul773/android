package com.example.ekhoney.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ekhoney.Modals.Area1Modal;
import com.example.ekhoney.R;

import java.util.ArrayList;

public class Area1Adaptor extends ArrayAdapter<Area1Modal> {
    public Area1Adaptor(@NonNull Context context, ArrayList<Area1Modal> area1ModalArrayList) {
        super(context,0, area1ModalArrayList);
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
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_spinner_item,parent,false);
        TextView spinner_text = convertView.findViewById(R.id.spinner_text);
        Area1Modal modal = getItem(position);
        if(modal != null){
            spinner_text.setText(modal.getTitle());
        }
        return convertView;
    }
}
