package com.example.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
public class showtreefragment extends Fragment {

    private TextView idmachine,ofmoist,ofph,oflight,sorttext,mnxofmoist,mnxofph,mnxoflight;
    private ImageView sortimage;
    public String idmac,nph,xph;
    private RecyclerView recyclerView;
    private List<recycelrpuklaidee> listpuk;
    public recycelrpuklaideeAdapter RecycelrpuklaideeAdapter;


    public showtreefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_showtreefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idmachine = view.findViewById(R.id.numbermachine);
        ofmoist = view.findViewById(R.id.ofmoist);
        ofph = view.findViewById(R.id.ofph);
        oflight = view.findViewById(R.id.oflight);
        sorttext = view.findViewById(R.id.sorttext);
        sortimage = view.findViewById(R.id.sortimage);
        mnxofmoist = view.findViewById(R.id.mnxofmoist);
        mnxofph = view.findViewById(R.id.mnxofph);
        mnxoflight = view.findViewById(R.id.mnxoflight);
        sorttext.setText("แสดงทั้งหมด");

        String getArgument = getArguments().getString("key_value");
        idmac = getArgument;
        idmachine.setText(idmac);

        loaddatamysensor();

        recyclerView = view.findViewById(R.id.loaddatatree);
        RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

        sortimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortby();
            }
        });

        sorttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortby();
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listpuk = new ArrayList<>();
        loadtree();

    }

    private void sortby(){
        final String[] listItems = {"แสดงทั้งหมด","ความเหมาะสมของพื้นที่","ราคา","ตลาด","ฤดู","ผลการวิเคราะห์"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
        mbuilder.setTitle("เรียงตาม");
        mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    sorttext.setText("แสดงทั้งหมด");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loadtree();
                }
                if(i == 1){
                    sorttext.setText("เรียงตามความเหมาะสมของพื้นที่");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loadtreebyph();
                }
                else if(i == 2){
                    sorttext.setText("เรียงตามราคา");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loadtreebyprice();
                }
                else if(i == 3){
                    sorttext.setText("เรียงตามตลาด");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loaddemand();
                }
                else if(i == 4){
                    selectseason();
                }
                else if(i == 5){
                    sorttext.setText("ผลการวิเคราะห์");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loadallprocess();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = mbuilder.create();
        dialog.show();
    }


    private void selectseason(){
        final String[] listItems = {"ฤดุร้อน","ฤดูหนาว","ฤดูฝน"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
        mbuilder.setTitle("เลือกฤดูกาล");
        mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    sorttext.setText("ฤดูร้อน");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loadsummer();
                }
                if(i == 1){
                    sorttext.setText("ฤดูหนาว");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loadwinter();
                }
                else if(i == 2){
                    sorttext.setText("ฤดูฝน");
                    listpuk.clear();
                    RecycelrpuklaideeAdapter.notifyDataSetChanged();
                    loadrainy();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = mbuilder.create();
        dialog.show();
    }



    public void loaddatamysensor(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/loaddatasensor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String minmoist = posts.getString("minmoist");
                        String maxmoist = posts.getString("maxmoist");
                        String avgmoist = posts.getString("avgmoist");
                        String minph = posts.getString("minph");
                        String maxph = posts.getString("maxph");
                        String avgph = posts.getString("avgph");
                        String minlight = posts.getString("minlight");
                        String maxlight = posts.getString("maxlight");
                        String avglight = posts.getString("avglight");
                        ofmoist.setText(avgmoist);
                        ofph.setText(avgph);
                        oflight.setText(avglight);
                        mnxofmoist.setText("("+minmoist+" - "+maxmoist+")");
                        mnxofph.setText("("+minph+" - "+maxph+")");
                        mnxoflight.setText("("+minlight+" - "+maxlight+")");
                        nph = minph;
                        xph = maxph;

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
                params.put("idmache",idmac);
                params.put("iduser",myid);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadtree(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/loadtreebysort.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("idmache",idmac);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadtreebyprice(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/sortbyprice.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("idmache",idmac);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadtreebyph(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/treesortbyph.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("lowph",nph);
                params.put("hightph",xph);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadsummer(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/summer.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("idmache",idmac);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadwinter(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/winter.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("idmache",idmac);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadrainy(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/rainy.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("idmache",idmac);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loaddemand(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/demand.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("idmache",idmac);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadallprocess(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/treesortbyall.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String tid = posts.getString("tid");
                        String namet = posts.getString("namet");
                        String phl = posts.getString("phl");
                        String phh = posts.getString("phh");
                        String price = posts.getString("price");
                        String img = posts.getString("img");
                        String season = posts.getString("season");
                        String report = posts.getString("report");

                        SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

                        String kk = getArguments().getString("key_value");
                        listpuk.add(new recycelrpuklaidee(
                                tid,
                                report,
                                img,
                                kk,
                                myid,
                                namet));
                        recycelrpuklaideeAdapter RecycelrpuklaideeAdapter = new recycelrpuklaideeAdapter(getContext(),listpuk);
                        recyclerView.setAdapter(RecycelrpuklaideeAdapter);

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
                params.put("lowph",nph);
                params.put("hightph",xph);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
