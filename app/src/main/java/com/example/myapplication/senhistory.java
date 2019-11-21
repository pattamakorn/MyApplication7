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
public class senhistory extends Fragment {
    private RecyclerView recyclerView;
    private List<sensorhistory> listhis;
    public  String formacti;


    public senhistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_senhistory, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.histo);
        sensorhistoryAdapter SensorhistoryAdapter = new sensorhistoryAdapter(getContext(),listhis);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(SensorhistoryAdapter);
        Bundle b3 = getArguments();
        formacti= b3.getString("idma");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listhis = new ArrayList<>();
        loadsensor();
    }

    public void loadsensor(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/loadhitory7.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String dated = posts.getString("date");
                        String moistavg = posts.getString("moistavg");
                        String phavg = posts.getString("phavg");
                        String lightavg = posts.getString("lightavg");

                        listhis.add(new sensorhistory(
                                dated,
                                "1",
                                moistavg,
                                phavg,
                                lightavg));
                        sensorhistoryAdapter SensorhistoryAdapter = new sensorhistoryAdapter(getContext(),listhis);
                        recyclerView.setAdapter(SensorhistoryAdapter);
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
                params.put("idm",formacti);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
