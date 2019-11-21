package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akaita.android.circularseekbar.CircularSeekBar;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class startplant extends AppCompatActivity {

    private ImageView showtree;
    private TextView nametreee,plantingmoist,plantingph,plantinglight;

    public String idtree;
    private CircularSeekBar circularSeekBar;
    int ci = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startplant);

        showtree = findViewById(R.id.plantingimg);
        plantingmoist = findViewById(R.id.plantingmoist);
        plantingph = findViewById(R.id.plantingph);
        plantinglight = findViewById(R.id.plantinglight);

        plantingmoist.setText("50%");
        plantingph.setText("5.5");
        plantinglight.setText("100");

        nametreee = findViewById(R.id.plantingname);
        circularSeekBar = findViewById(R.id.circulbar);

        circularSeekBar.setProgressTextFormat(new DecimalFormat("###,###,###,##0"));
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {


                        circularSeekBar.setProgress(ci);

                        ci++;
                    }
                });
            }
        }, 0, 12000);

        circularSeekBar.setRingColor(Color.RED);

        circularSeekBar.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if(progress < 40) {circularSeekBar.setRingColor(Color.RED);}
                else if(progress >= 40 && progress < 70){circularSeekBar.setRingColor(Color.YELLOW);}
                else if(progress >= 70) {circularSeekBar.setRingColor(Color.GREEN);}
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        idtree = bundle.getString("Myidtree");
        //Toast.makeText(this,idtree, Toast.LENGTH_SHORT).show();
        loaddetailpuklaidee();

    }

    public void loaddetailpuklaidee(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/loadtreeonstart.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String treename = posts.getString("name");
                        String imgtree = posts.getString("img");
                        nametreee.setText(treename);
                        Glide.with(startplant.this).load(imgtree).into(showtree);
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
                params.put("id",idtree);
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
