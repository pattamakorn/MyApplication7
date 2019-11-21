package com.example.myapplication;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class addmachine extends Fragment {

    private RecyclerView recyclerView;
    private List<mymachine> listmach;

    private CardView scanqr,addid;
    public EditText addmac;

    Dialog myDialog;

    public mymachineAdapter MymachineAdapter;


    public addmachine() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addmachine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclermachine);
        scanqr = view.findViewById(R.id.inqrcode);
        addid = view.findViewById(R.id.inputid);

        myDialog = new Dialog(getContext());
        myDialog.setContentView(R.layout.addid_dialog);

        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),scanqrcode.class));
            }
        });

        addid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "addid", Toast.LENGTH_SHORT).show();
                addmac = myDialog.findViewById(R.id.inputidmachinenow);
                TextView ok = myDialog.findViewById(R.id.addm);
                TextView can = myDialog.findViewById(R.id.canceladd);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),addmac.getText().toString(), Toast.LENGTH_SHORT).show();
                        listmach.clear();
                        MymachineAdapter.notifyDataSetChanged();
                        loadmachine();
                        addmachine();
                        myDialog.cancel();
                    }
                });

                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.cancel();
                    }
                });

                myDialog.show();
            }
        });

        MymachineAdapter = new mymachineAdapter(getContext(),listmach);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(MymachineAdapter);

//        MymachineAdapter = new mymachineAdapter(getContext(),listmach);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        recyclerView.setAdapter(MymachineAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listmach = new ArrayList<>();
        loadmachine();
    }

    public void loadmachine(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/loadmachine.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String idmy = posts.getString("id");
                        String mysen = posts.getString("my_sensor");
                        String address = posts.getString("address");
                        listmach.add(new mymachine(
                                ""+idmy,
                                ""+mysen,
                                ""+address));
                        MymachineAdapter = new mymachineAdapter(getContext(),listmach);
                        recyclerView.setAdapter(MymachineAdapter);
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
                params.put("user",myid);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void addmachine(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/addmachine.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseStudent",response.toString());
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("abc",error.toString());
                        // Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferences prefs = getActivity().getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String showidpre = prefs.getString("My_user","NoId");
                params.put("member",showidpre);
                params.put("sensor",addmac.getText().toString());
                params.put("address","Buriram");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
