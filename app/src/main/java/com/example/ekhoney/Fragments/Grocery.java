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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ekhoney.Adaptors.CatAAdaptor;
import com.example.ekhoney.Modals.CatAModel;
import com.example.ekhoney.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Grocery extends Fragment {

    View view;
    ImageView grocery_cata_img;
    TextView grocey_header;
    String cata_id, catb_id;
    RecyclerView grocery_catb_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grocery, container, false);

        // #### GET IDS ####
        grocey_header = view.findViewById(R.id.grocey_header);
        grocery_cata_img = view.findViewById(R.id.grocery_cata_img);
        grocery_catb_list = view.findViewById(R.id.grocery_catb_list);
        // #### GET IDS ####


        // #### GET BUNDLE VALUE ####
        Bundle bundle = getArguments();
        if(bundle != null){
            cata_id = String.valueOf(bundle.getInt("cata_id"));
            String cata_img = bundle.getString("cata_img");
            String cata_title = bundle.getString("cata_title");
            grocey_header.setText("Categories of " + cata_title);
            Glide.with(getContext()).load(cata_img).into(grocery_cata_img);
        }
        // #### GET BUNDLE VALUE ####


        GetCatbCategories();
        return view;
    }

    private void GetCatbCategories() {

        ArrayList<CatAModel> catAModelArrayList = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        grocery_catb_list.setLayoutManager(layoutManager);

        CatAAdaptor catAAdaptor = new CatAAdaptor(getContext(),catAModelArrayList);
        grocery_catb_list.setAdapter(catAAdaptor);

        // ## CLICK ON CATB ITEM ##
        catAAdaptor.setOnCataClicklistener(new CatAAdaptor.OnCataClicklistener() {
            @Override
            public void OnCataclick(int position) {
                catb_id = catAModelArrayList.get(position).getId();
                String img2 = catAModelArrayList.get(position).getImg2();
                String title = catAModelArrayList.get(position).getTitle();

                Fragment fragment = new Category();
                Bundle bundle = new Bundle();
                bundle.putString("catb_id",catb_id);
                bundle.putString("catb_img",img2);
                bundle.putString("catb_title",title);
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                //Toast.makeText(getContext(), "grocer", Toast.LENGTH_SHORT).show();

            }
        });
        // ## CLICK ON CATB ITEM ##

        String url = "https://apiapp773.000webhostapp.com/user_api/show_catb.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                catAModelArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String title = object.getString("title");
                        String image = object.getString("img");
                        String image2 = object.getString("img2");
                        String img = "https://apiapp773.000webhostapp.com/owner/product_images/ekhoney.jpg";
                        String img2 = "https://apiapp773.000webhostapp.com/owner/product_images/ekhoney.jpg";

                        CatAModel catAModel = new CatAModel(id, title, img, img2);
                        catAModelArrayList.add(catAModel);
                        catAAdaptor.notifyDataSetChanged();
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
                Map<String, String> params = new HashMap<String,String>();
                params.put("cata_id", cata_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}