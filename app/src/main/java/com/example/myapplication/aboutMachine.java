package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class aboutMachine extends AppCompatActivity {

    private TextView showidm,showlocal,ph,moist,light,care,improvement,betweenph;
    public String idmac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_machine);

        showidm = findViewById(R.id.showIDM);
        showlocal = findViewById(R.id.showLocal);
        ph = findViewById(R.id.valueph);
        moist = findViewById(R.id.valuemoist);
        light = findViewById(R.id.valuelight);
        betweenph = findViewById(R.id.betweenph);
        senhistory Senhistory = new senhistory();
        Bundle b2 = new Bundle();
        b2.putString("idma","sensor01");
        Senhistory.setArguments(b2);
        getSupportFragmentManager().beginTransaction().add(R.id.frameabout,Senhistory).commit();
        care = findViewById(R.id.care);
        improvement = findViewById(R.id.improvement);

        Bundle bundle = getIntent().getExtras();
        idmac = bundle.getString("Myidmachine");
        String shadd= bundle.getString("Myaddress");


        loadsensor();


        showidm.setText(idmac);
        showlocal.setText(shadd);
    }

    public void loadsensor(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/aboutmachine.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String phavg = posts.getString("phavg");
                        String moistavg = posts.getString("moistavg");
                        String lightavg = posts.getString("lightavg");
                        String phmin = posts.getString("phmin");
                        String phmax = posts.getString("phmax");
                        ph.setText(phavg);
                        moist.setText(moistavg+"%");
                        light.setText(lightavg);
                        betweenph.setText("("+phmin+" - "+phmax+")");

                        if(phavg == null){
                            Toast.makeText(aboutMachine.this, "Not Data", Toast.LENGTH_SHORT).show();

                        }else if(phavg != null){
                            double d = Double.parseDouble(phavg);
                            if(d <= 4.4){
                                improvement.setText("การปรับปรุง:\n  ใส่ปูนขาวอัตราสูงร่วมกับปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 4.5 && d <= 5.0){
                                improvement.setText("การปรับปรุง:\n  ใส่ปูนขาวอัตราต่ำ-กลาง ร่วมกับปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 5.1 && d <= 5.5){
                                improvement.setText("การปรับปรุง:\n  ใส่ปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 5.5 && d <= 6.0){
                                improvement.setText("การปรับปรุง:\n  ใส่ปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 6.1 && d <= 6.5){
                                improvement.setText("การปรับปรุง:\n  ใส่ปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 6.6 && d <= 7.2){
                                improvement.setText("การปรับปรุง:\n  ใส่ปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 7.3 && d <= 7.8){
                                improvement.setText("การปรับปรุง:\n  ดินของคุณเหมาะกับการเพราะปลูก");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 7.9 && d <= 8.4){
                                improvement.setText("การปรับปรุง:\n  ใส่ยิปซัมอัตราปานกลางร่วมกับปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 8.5 && d < 9.0){
                                improvement.setText("การปรับปรุง:\n  ใส่ยิปซัมอัตราสูงร่วมกับปุ๋ยอินทรีย์");
                                care.setText("การดูแล:\n ");
                            }else if(d >= 9.0){
                                improvement.setText("การปรับปรุง:\n  ใส่ปูนขาว");
                                care.setText("การดูแล:\n ");
                            }
                        }
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
                params.put("user",myid);
                params.put("idm",idmac);
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
