package com.example.ekhoney.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.ekhoney.Adaptors.Area1Adaptor;
import com.example.ekhoney.Modals.Area1Modal;
import com.example.ekhoney.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Register extends Fragment {

    Spinner reg_area1_list, reg_area2_list;
    TextView reg_error;
    String area1_id;
    EditText reg_moblie, reg_house,reg_pass, reg_cpass;
    Button reg_btn;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);


        // #### GET IDS ####
        reg_area1_list = view.findViewById(R.id.reg_area1_list);
        reg_area2_list = view.findViewById(R.id.reg_area2_list);
        reg_moblie = view.findViewById(R.id.reg_mobile);
        reg_house = view.findViewById(R.id.reg_house);
        reg_pass = view.findViewById(R.id.reg_pass);
        reg_cpass = view.findViewById(R.id.reg_cpass);
        reg_btn = view.findViewById(R.id.reg_btn);
        reg_error = view.findViewById(R.id.reg_error);
        // #### GET IDS ####


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Area1Modal area1Modal = (Area1Modal) reg_area1_list.getSelectedItem();
                Area1Modal area2Modal = (Area1Modal) reg_area2_list.getSelectedItem();
                String area1 = area1Modal.getId();
                String area2 = area2Modal.getId();
                String mobile = reg_moblie.getText().toString();
                String house = reg_house.getText().toString();
                String pass = reg_pass.getText().toString();
                String cpass = reg_cpass.getText().toString();
                String user = area1 + " " + area2 + " " + pass + " " + house + " " + mobile;
                if(pass.isEmpty()){
                    reg_error.setText("Enter password");
                } else if (!pass.equals(cpass)) {
                    reg_error.setText("Password does not match");
                } else if (house.isEmpty()) {
                    reg_error.setText("House/Office field is empty");
                } else {
                    reg_error.setText(user);
                }
            }
        });

        GetArea1();
        return view;
    }

    private void GetArea1() {
        ArrayList<Area1Modal> area1ModalArrayList = new ArrayList<>();
        Area1Adaptor area1Adaptor = new Area1Adaptor(getContext(),area1ModalArrayList);
        reg_area1_list.setAdapter(area1Adaptor);


        // ## AREA 1 CLICK LISTENER ##
        reg_area1_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area1Modal area1Modal1 = (Area1Modal) parent.getItemAtPosition(position);
                area1_id = area1Modal1.getId();
                String area1_title = area1Modal1.getTitle();
                GetArea2();
                //Toast.makeText(getContext(), area1_title, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        // #### AREA 1 CLICK LISTENER ####



        String url = "https://apiapp773.000webhostapp.com/user_api/active_area1.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                area1ModalArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String title = object.getString("title");
                        Area1Modal area1Modal = new Area1Modal(id,title);
                        area1ModalArrayList.add(area1Modal);
                        area1Adaptor.notifyDataSetChanged();
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

    private void GetArea2() {
        ArrayList<Area1Modal> area1ModalArrayList = new ArrayList<>();
        Area1Adaptor adaptor = new Area1Adaptor(getContext(),area1ModalArrayList);
        reg_area2_list.setAdapter(adaptor);

        String url = "https://apiapp773.000webhostapp.com/user_api/active_area2.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                area1ModalArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String title = object.getString("title");
                        Area1Modal area1Modal = new Area1Modal(id,title);
                        area1ModalArrayList.add(area1Modal);
                        adaptor.notifyDataSetChanged();
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
                params.put("area1_id",area1_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}