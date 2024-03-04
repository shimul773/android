package com.example.ekhoney.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.example.ekhoney.Modals.ProductsModal;
import com.example.ekhoney.Modals.ShopCategoryModal;
import com.example.ekhoney.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Category extends Fragment implements ProductAdaptor.OnProductImgclicklistener, ProductAdaptor.OnMinusBTNclicklistner, ProductAdaptor.OnPlusBTNclicklistner{

    View view;
    TextView category_header_title;
    String catb_id, catc_id, product_id;
    Spinner category_catc_list;
    RecyclerView category_product_list;
    ArrayList<ShopCategoryModal> shopCategoryModalArrayList;
    ArrayList<ProductsModal> productsModalArrayList;
    ProductAdaptor productAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        // #### GET IDS STARTS ####
        category_header_title = view.findViewById(R.id.category_header_title);
        category_catc_list = view.findViewById(R.id.category_catc_list);
        category_product_list = view.findViewById(R.id.category_product_list);
        // #### GET IDS ENDS####


        // #### GET BUNDLE VALUES ####
        Bundle bundle = getArguments();
        if(bundle != null){
            catb_id = bundle.getString("catb_id");
            String catb_title = bundle.getString("catb_title");
            //category_header_title.setText(catb_title);
        }
        // #### GET BUNDLE VALUES ####


        GetcatcCateGory();
        return view;
    }

    private void GetcatcCateGory() {
        shopCategoryModalArrayList = new ArrayList<>();
        ShopCategoryAdaptor shopCategoryAdaptor = new ShopCategoryAdaptor(getContext(),shopCategoryModalArrayList);
        category_catc_list.setAdapter(shopCategoryAdaptor);

        category_catc_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ShopCategoryModal shopCategory = (ShopCategoryModal) parent.getItemAtPosition(position);
                if(shopCategory != null){
                    String title = shopCategory.getTitle();
                    Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                    GetCatcProducts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        String url = "https://apiapp773.000webhostapp.com/user_api/show_catc.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shopCategoryModalArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succ = jsonObject.getString("succ");
                    category_header_title.setText(succ);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(succ.equalsIgnoreCase("yes")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            catc_id = object.getString("id");
                            String title = object.getString("title");
                            String products = object.getString("products");

                            ShopCategoryModal modal = new ShopCategoryModal(catc_id,title,products);
                            shopCategoryModalArrayList.add(modal);
                            shopCategoryAdaptor.notifyDataSetChanged();
                        }
                    }
                    else {
                        //not found
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
                params.put("catb_id",catb_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void GetCatcProducts() {

        productsModalArrayList = new ArrayList<>();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        category_product_list.setLayoutManager(layoutManager);

        productAdaptor = new ProductAdaptor(getContext(),productsModalArrayList);
        category_product_list.setAdapter(productAdaptor);
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

    @Override
    public void OnMinusBTNclic(int position) {
        product_id = productsModalArrayList.get(position).getId();
        Toast.makeText(getContext(), product_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnPlusBTNclick(int position) {
        product_id = productsModalArrayList.get(position).getId();
        Toast.makeText(getContext(), product_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnProductImgclick(int position) {
        product_id = productsModalArrayList.get(position).getId();
        Toast.makeText(getContext(), product_id, Toast.LENGTH_SHORT).show();
    }
}
