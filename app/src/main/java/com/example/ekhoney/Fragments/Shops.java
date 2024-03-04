package com.example.ekhoney.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ekhoney.Adaptors.ShopsAdaptor;
import com.example.ekhoney.Modals.ShopsModal;
import com.example.ekhoney.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Shops extends Fragment {

    View view;
    TextView shops_header;
    ImageView shops_cata_img;
    RecyclerView shops_shop_list;
    String cata_id,  cata_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shops, container, false);


        // #### GET IDS ####
        shops_header = view.findViewById(R.id.shops_header);
        shops_cata_img = view.findViewById(R.id.shops_cata_img);
        shops_shop_list = view.findViewById(R.id.shops_shop_list);
        // #### GET IDS ####



        // #### BUNDLE VALUES ####
        Bundle bundle = getArguments();
        if(bundle != null){
            cata_id = String.valueOf(bundle.getInt("cata_id"));
            String cata_img = bundle.getString("cata_img");
            cata_title = bundle.getString("cata_title");
            shops_header.setText("List of " + cata_title);
            Glide.with(getContext()).load(cata_img).into(shops_cata_img);
        }
        // #### BUNDLE VALUES ####


        GetAllShops();
        return view;
    }

    private void GetAllShops() {
        ArrayList<ShopsModal> shopsModalArrayList = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        shops_shop_list.setLayoutManager(layoutManager);

        ShopsAdaptor shopsAdaptor = new ShopsAdaptor(getContext(),shopsModalArrayList);
        shops_shop_list.setAdapter(shopsAdaptor);

        // #### ON CLICK ####
        shopsAdaptor.setOnShopclicklistener(new ShopsAdaptor.OnShopclicklistener() {
            @Override
            public void OnShopclick(int position) {
                String shop_id = shopsModalArrayList.get(position).getId();
                String shop_title = shopsModalArrayList.get(position).getTitle();
                String shop_img = shopsModalArrayList.get(position).getImg();

                Fragment fragment = new Shop();
                Bundle bundle = new Bundle();
                bundle.putString("shop_id",shop_id);
                bundle.putString("shop_title",shop_title);
                bundle.putString("shop_img", shop_img);
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            }
        });
        // #### ON CLICK ####


        String url = "https://apiapp773.000webhostapp.com/user_api/shops.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shopsModalArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succ = jsonObject.getString("succ");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(succ.equalsIgnoreCase("yes")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String title = object.getString("title");
                            String image = object.getString("img");
                            String cata = object.getString("cata");
                            String img = "https://apiapp773.000webhostapp.com/owner/images" + image;

                            ShopsModal shopsModal = new ShopsModal(id, title,img,cata);
                            shopsModalArrayList.add(shopsModal);
                            shopsAdaptor.notifyDataSetChanged();
                        }
                    }
                    else {
                        // no item found
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
                Map<String, String> param = new HashMap<String, String>();
                param.put("cata_id", cata_id);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}