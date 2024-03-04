package com.example.ekhoney.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ekhoney.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class Login extends Fragment {

    View view;
    Button log_btn;
    TextView log_error;
    EditText log_username, log_pass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        log_btn = view.findViewById(R.id.log_btn);
        log_pass = view.findViewById(R.id.log_pass);
        log_username = view.findViewById(R.id.log_username);
        log_error = view.findViewById(R.id.log_error);

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = log_username.getText().toString();
                String pass = log_pass.getText().toString();


                if(username.isEmpty()){
                    log_error.setText("Mobile is empty.");
                } else if (pass.isEmpty()) {
                    log_error.setText("Password is empty");
                }
                else {
                    String url = "https://apiapp773.000webhostapp.com/user_api/login.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String succ = jsonObject.getString("succ");
                                if(succ.equalsIgnoreCase("yes")){
                                    SharedPreferences preferences = getActivity().getSharedPreferences("UserLogIN", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("username",username);
                                    editor.apply();
                                }
                                else {
                                    log_error.setText(succ);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            log_error.setText(error.getMessage());
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username",username);
                            params.put("pass",pass);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(request);
                }


            }
        });
        return view;
    }
}