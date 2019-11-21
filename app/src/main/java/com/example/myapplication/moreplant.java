package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class moreplant extends AppCompatActivity {
    private RatingBar rate;
    private ImageView picimg;
    private TextView hownametree,howplant,pon;
    public String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreplant);

        picimg = findViewById(R.id.morepic);
        rate = findViewById(R.id.rate);
        hownametree = findViewById(R.id.howtoplant);
        howplant = findViewById(R.id.detailplant);
        pon = findViewById(R.id.pon);

        Bundle bundle = getIntent().getExtras();
        text = bundle.getString("Myidtree");
        Toast.makeText(this, "kooo="+text, Toast.LENGTH_SHORT).show();

        loaddatamysensor();

    }


    public void loaddatamysensor(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/more.php", new Response.Listener<String>() {
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
                        String ponm = posts.getString("pon");
                        String back = posts.getString("back");

                        double d = Double.parseDouble(back);
                        Float f= Float.parseFloat(back);
                        int value = Integer.parseInt(back);

                        rate.setMax(5);
                        rate.setNumStars(5);
                        rate.setRating(value);
                        rate.setStepSize((float) 0.5);

                        Glide.with(moreplant.this).load(img).into(picimg);
                        hownametree.setText("วิธีการปลูก"+namet);
                        howplant.setText(report);
                        pon.setText(ponm+" กิโลกรัม/ไร่");

                        SharedPreferences prefs = getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                        String myid = prefs.getString("My_user","NoId");

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
                params.put("idoftree",text);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
