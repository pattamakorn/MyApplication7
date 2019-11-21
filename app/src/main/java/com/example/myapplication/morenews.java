package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class morenews extends AppCompatActivity {

    private TextView texta,date,time,link;
    private ImageView showlongimg;
    public String getvaluenews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morenews);

        texta = findViewById(R.id.shownews);
//        date = findViewById(R.id.longdate);
//        time = findViewById(R.id.longtime);
        showlongimg = findViewById(R.id.longimg);
//        link = findViewById(R.id.link);


        Bundle bundle = getIntent().getExtras();
        getvaluenews = bundle.getString("MyValue");

        loadnewsmore();

    }

    public void loadnewsmore(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/loadnewsmore.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String sidnews = posts.getString("newsid");
                        String shotimgpost = posts.getString("shotimg");
                        String shotvalue = posts.getString("shottoppic");
                        String shotdate = posts.getString("newsdate");
                        String shottime = posts.getString("newstime");
                        String longimgpost = posts.getString("longimg");
                        String longvalue = posts.getString("longtoppic");
                        String longcr = posts.getString("cr");
                        Toast.makeText(morenews.this,sidnews, Toast.LENGTH_SHORT).show();
                        texta.setText(longvalue);
//                        link.setText(longcr);
//                        date.setText(shotdate);
//                        time.setText(shottime);
                        Glide.with(morenews.this).load(longimgpost).into(showlongimg);

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
                SharedPreferences prefs = getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String myid = prefs.getString("My_user","NoId");
                Map<String,String> params = new HashMap<>();
                params.put("id",getvaluenews);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
