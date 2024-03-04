package com.example.ekhoney.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ekhoney.R;


public class Product_details extends Fragment {

    View view;
    TextView details_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_details, container, false);

        details_title = view.findViewById(R.id.details_title);

        Bundle bundle = getArguments();
        if(bundle != null){
            String title = bundle.getString("title");
            details_title.setText(title);
        }
        else {
            // go to another place
        }

        return view;
    }
}