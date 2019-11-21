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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class dividend extends Fragment {

    private RecyclerView recyclerView;
    private List<dividenddetail> listmoney;
    private TextView allmoney,datenow;
    private ImageView picpro;


    public dividend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dividend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycdiviend);
        allmoney = view.findViewById(R.id.allmoney);
        picpro = view.findViewById(R.id.propic);
        datenow = view.findViewById(R.id.datenow);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ct = simpleDateFormat.format(new Date());
        datenow.setText(ct);


        dividenddetailAdapter DividenddetailAdapter = new dividenddetailAdapter(getContext(),listmoney);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(DividenddetailAdapter);

        loadmoney();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listmoney = new ArrayList<>();
        loaddemoney();
    }

    public void loadmoney(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/loadallmoney.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String summoney = posts.getString("summoney");
                        allmoney.setText(summoney+" บาท");
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
                SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String myid = prefs.getString("My_user","NoId");
                Map<String,String> params = new HashMap<>();
                params.put("id",myid);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loaddemoney(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/dividendprofile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String pic = posts.getString("pic");
                        String deemoney = posts.getString("money");
                        String deedate = posts.getString("ddate");
                        Glide.with(getActivity()).load(pic).into(picpro);
                        listmoney.add(new dividenddetail(
                                "1"
                                ,deedate
                                ,deemoney+" บาท"));
                        dividenddetailAdapter DividenddetailAdapter = new dividenddetailAdapter(getContext(),listmoney);
                        recyclerView.setAdapter(DividenddetailAdapter);

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
                SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String myid = prefs.getString("My_user","NoId");
                Map<String,String> params = new HashMap<>();
                params.put("idu",myid);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
