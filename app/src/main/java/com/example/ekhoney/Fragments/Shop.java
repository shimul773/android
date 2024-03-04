package com.example.ekhoney.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
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
import com.example.ekhoney.Adaptors.ShopCategoryAdaptor;
import com.example.ekhoney.Adaptors.ShopProductAdaptor;
import com.example.ekhoney.Modals.ProductsModal;
import com.example.ekhoney.Modals.ShopCategoryModal;
import com.example.ekhoney.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Shop extends Fragment implements ProductAdaptor.OnProductImgclicklistener, ProductAdaptor.OnPlusBTNclicklistner, ProductAdaptor.OnMinusBTNclicklistner{

    View view;
    TextView shop_shop_title, shop_shop_error;
    String shop_id, shop_title, catc_id, catb_id;
    Spinner shop_shop_spinner;
    RecyclerView shop_product_list;
    ArrayList<ProductsModal> productsModalArrayList;
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);



        // #### GET IDS ####
        shop_shop_title = view.findViewById(R.id.shop_shop_title);
        shop_shop_spinner = view.findViewById(R.id.shop_shop_spinner);
        shop_shop_error = view.findViewById(R.id.shop_shop_error);
        shop_product_list = view.findViewById(R.id.shop_product_list);
        // #### GET IDS ####



        // #### BUNDLE VALUES ####
        Bundle bundle = getArguments();
        if(bundle != null){
            shop_id = bundle.getString("shop_id");
            shop_title = bundle.getString("shop_title");
            shop_shop_title.setText(shop_title);
        }
        // #### BUNDLE VALUES ####


        // #### SHAREDPREFERENCE ####
        username = "01762303476";
        // #### SHAREDPREFERENCE ####

        GetShopCategory();
        return view;
    }

    private void GetShopCategory() {
        ArrayList<ShopCategoryModal> shopCategoryModalArrayList = new ArrayList<>();
        ShopCategoryAdaptor shopCategoryAdaptor = new ShopCategoryAdaptor(getContext(), shopCategoryModalArrayList);
        shop_shop_spinner.setAdapter(shopCategoryAdaptor);

        shop_shop_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ShopCategoryModal shopCategory = (ShopCategoryModal) parent.getItemAtPosition(position);
                if(shopCategory != null){
                    catc_id = shopCategory.getId();
                    String title = shopCategory.getTitle();
                    GetShopProducts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        String url = "https://apiapp773.000webhostapp.com/user_api/shop_category.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shopCategoryModalArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    catb_id = jsonObject.getString("catb_id");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String title = object.getString("title");
                        String products = object.getString("products");
                        ShopCategoryModal shopCategoryModal = new ShopCategoryModal(id,title,products);
                        shopCategoryModalArrayList.add(shopCategoryModal);
                        shopCategoryAdaptor.notifyDataSetChanged();
                    }
                }
                catch (Exception e ){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("shop_id", shop_id);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void GetShopProducts() {
        productsModalArrayList = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        shop_product_list.setLayoutManager(layoutManager);

        ProductAdaptor productAdaptor = new ProductAdaptor(getContext(),productsModalArrayList);
        shop_product_list.setAdapter(productAdaptor);
        productAdaptor.setOnProductImgclicklistener(this);
        productAdaptor.setOnPlusBTNclicklistner(this);
        productAdaptor.setOnMinusBTNclicklistner(this);

        String url = "https://apiapp773.000webhostapp.com/user_api/shop_product.php";
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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("catc_id", catc_id);
                params.put("catb_id",catb_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }




    // #### MINUS BTN CLICK LISTENER ####
    @Override
    public void OnMinusBTNclic(int position) {
        //
    }
    // #### MINUS BTN CLICK LISTENER ####


    // #### PLUS BTN CLICK LISTENER ####
    @Override
    public void OnPlusBTNclick(int position) {
        String product_id = productsModalArrayList.get(position).getId();
        String url = "https://apiapp773.000webhostapp.com/user_api/add_to_cart2.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id",product_id);
                params.put("username",username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
    // #### PLUS BTN CLICK LISTENER ####




    // #### IMG CLICK LISTENER ####
    @Override
    public void OnProductImgclick(int position) {
        //
    }
    // #### IMG CLICK LISTENER ####
}