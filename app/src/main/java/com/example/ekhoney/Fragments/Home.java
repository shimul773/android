package com.example.ekhoney.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ekhoney.Adaptors.CatAAdaptor;
import com.example.ekhoney.Modals.CatAModel;
import com.example.ekhoney.R;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Home extends Fragment {
    View view;
    ImageCarousel home_carousel1;
    RecyclerView home_cata_list;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        // #### GET IDS STARTS ####
        home_carousel1 = view.findViewById(R.id.home_carousel1);
        home_cata_list = view.findViewById(R.id.home_cata_list);
        // #### GET IDS ENDS ####

        GetHomeImgSlider();
        GetCataCategory();
        return view;
    }

    private void GetCataCategory() {
        ArrayList<CatAModel> catAModelArrayList = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        home_cata_list.setLayoutManager(layoutManager);

        CatAAdaptor catAAdaptor = new CatAAdaptor(getContext(),catAModelArrayList);
        home_cata_list.setAdapter(catAAdaptor);

        // ## CLICK ON CATA ITEM ##
        catAAdaptor.setOnCataClicklistener(new CatAAdaptor.OnCataClicklistener() {
            @Override
            public void OnCataclick(int position) {
                Set<Integer> ids = new HashSet<>(Arrays.asList(2,3,4));
                int id = Integer.parseInt(catAModelArrayList.get(position).getId());
                String img2 = catAModelArrayList.get(position).getImg2();
                String title = catAModelArrayList.get(position).getTitle();
                if(ids.contains(id)){
                    Fragment fragment = new Shops();
                    Bundle bundle = new Bundle();
                    bundle.putInt("cata_id",id);
                    bundle.putString("cata_img",img2);
                    bundle.putString("cata_title",title);
                    fragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    //Toast.makeText(getContext(), "owner", Toast.LENGTH_SHORT).show();
                }
                else {
                    Fragment fragment = new Grocery();
                    Bundle bundle = new Bundle();
                    bundle.putInt("cata_id", id);
                    bundle.putString("cata_img",img2);
                    bundle.putString("cata_title",title);
                    fragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    //Toast.makeText(getContext(), "grocer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // ## CLICK ON CATA ITEM ##

        String url = "https://apiapp773.000webhostapp.com/user_api/show_cata.php";
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void GetHomeImgSlider() {
        home_carousel1.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();
        String url = "https://apiapp773.000webhostapp.com/user_api/show_all_products.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String image = object.getString("img");
                        String img = "https://apiapp773.000webhostapp.com/owner/product_images/" + image;
                        CarouselItem carouselItem = new CarouselItem(img);
                        list.add(carouselItem);
                        home_carousel1.setData(list);
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



}