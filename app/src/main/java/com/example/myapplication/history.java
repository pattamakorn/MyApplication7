package com.example.myapplication;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class history extends Fragment {

    private RecyclerView recyclerView;
    private List<dhistory> listtory;
    private String URL_Profile = "http://203.154.83.137/puklaidee/loadprofile.php";

    private ImageView imgprofilehis;
    private TextView fullnamehistory,rearr,count,sum;
    public  String sttta,stoppp;


    public history() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.historyplant);
        imgprofilehis = view.findViewById(R.id.imgprofilehis);
        fullnamehistory = view.findViewById(R.id.fullnamehistory);
        rearr = view.findViewById(R.id.rearr);
        count = view.findViewById(R.id.count);
        sum = view.findViewById(R.id.sum);


        dhistoryAdapter DhistoryAdapter = new dhistoryAdapter(getContext(),listtory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(DhistoryAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadprofile();
        loadinthistory();
        listtory = new ArrayList<>();
        loadrecycler();
    }

    public void loadprofile(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,URL_Profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String fname = posts.getString("fname");
                        String lname = posts.getString("lname");
                        String ye = posts.getString("yearold");
                        String tel = posts.getString("tel");
                        String ar = posts.getString("area");
                        String ad = posts.getString("address");
                        String im = posts.getString("img");
                        Glide.with(getActivity()).load(im).into(imgprofilehis);
                        fullnamehistory.setText(fname+" "+lname);
                        rearr.setText(ar);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String showidpre = prefs.getString("My_user","NoId");
                params.put("user",showidpre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadinthistory(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,"http://203.154.83.137/puklaidee/detailhistory.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String hcount = posts.getString("count");
                        String hsum = posts.getString("sum");
                        int sumdividend = Integer.parseInt(hsum);
                        if(sumdividend > 999){
                            float d= (float) sumdividend/1000;
                            sum.setText(String.valueOf(d)+" k");
                        }else if(sumdividend <= 999){
                            sum.setText(String.valueOf(sumdividend));
                        }
                        count.setText(hcount);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String showidpre = prefs.getString("My_user","NoId");
                params.put("idu",showidpre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadrecycler(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,"http://203.154.83.137/puklaidee/recyclerhistory.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String idro = posts.getString("id_datatree");
                        String ntree = posts.getString("nametree");
                        String km = posts.getString("Km");
                        String tstart = posts.getString("start_tree");
                        String estart = posts.getString("end_tree");
                        String ssta = posts.getString("status");
                        if(ssta.equals("1")){
                            sttta = "สิ้นสูตรการปลูก";
                        }else if(ssta.equals("0")){
                            sttta = "กำลังปลูก";
                        }else if(estart == null){
                            stoppp = tstart;
                        }else if(estart != null){
                            stoppp = tstart+" - "+estart;
                        }

                        listtory.add(new dhistory(
                                idro,
                                ntree,
                                "น้ำหนัก "+km+" กิโลกรัม",
                                "เริ่มปลูก: "+tstart,
                                sttta,
                                posts.getString("img")));
                        dhistoryAdapter DhistoryAdapter = new dhistoryAdapter(getContext(),listtory);
                        recyclerView.setAdapter(DhistoryAdapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String showidpre = prefs.getString("My_user","NoId");
                params.put("user",showidpre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
