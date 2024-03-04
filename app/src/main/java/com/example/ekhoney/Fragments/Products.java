package com.example.ekhoney.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ekhoney.Adaptors.ProductAdaptor;
import com.example.ekhoney.Modals.ProductsModal;
import com.example.ekhoney.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Products extends Fragment implements ProductAdaptor.OnProductImgclicklistener, ProductAdaptor.OnPlusBTNclicklistner, ProductAdaptor.OnMinusBTNclicklistner{
    View view;
    RecyclerView products_products_list;
    ArrayList<ProductsModal> productsModalArrayList;
    ProductAdaptor productAdaptor;
    String  username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);

        products_products_list = view.findViewById(R.id.products_products_list);
        GetProducts();
        return view;
    }

    private void GetProducts() {
        productsModalArrayList = new ArrayList<>();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        products_products_list.setLayoutManager(layoutManager);

        productAdaptor = new ProductAdaptor(getContext(),productsModalArrayList);
        products_products_list.setAdapter(productAdaptor);
        productAdaptor.setOnProductImgclicklistener(this);
        productAdaptor.setOnPlusBTNclicklistner(this);
        productAdaptor.setOnMinusBTNclicklistner(this);

        // ## IMG CLICK LISTENER
        // ## IMG CLICK LISTENER

        String url = "https://apiapp773.000webhostapp.com/user_api/show_all_products.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                productsModalArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    String succ = jsonObject.getString("succ");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String title = object.getString("title");
                        String price = object.getString("price");
                        String mrp = object.getString("mrp");
                        String image = object.getString("img");
                        String cata = object.getString("cat_a");
                        String catb = object.getString("cat_b");
                        String catc = object.getString("cat_c");
                        String brand = object.getString("brand");
                        String desc = object.getString("desc");
                        String cart = object.getString("cart");

                        String img = "https://apiapp773.000webhostapp.com/owner/product_images/" + image;
                        ProductsModal productsModal = new ProductsModal(id,title,price,mrp,img,cata,catb,catc,brand,desc,cart);
                        productsModalArrayList.add(productsModal);
                        productAdaptor.notifyDataSetChanged();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }



    @Override
    public void OnProductImgclick(int position) {
        String id = productsModalArrayList.get(position).getId();
        String title = productsModalArrayList.get(position).getTitle();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("title",title);

        Fragment fragment = new Product_details();
        fragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }

    @Override
    public void OnPlusBTNclick(int position) {
        //increase the number by 1 for every click of textview which id is cart_quantity
    }

    @Override
    public void OnMinusBTNclic(int position) {
        //
    }

}