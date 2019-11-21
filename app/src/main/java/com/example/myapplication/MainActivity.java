package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String URL_Profile = "http://203.154.83.137/puklaidee/loadprofile.php";
    public TextView profilename,mytel,myage,myarea,myaddress;
    public ImageView mypro;

    private TextView nameprofile;

    private AppBarConfiguration mAppBarConfiguration;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new feed()).commit();
                    return true;
                case R.id.timetable:
                    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new puklaidee()).commit();
                    return true;
                case R.id.checknames:
                    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new dividend()).commit();
                    return true;
                case R.id.gps:
                    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new history()).commit();
                    return true;
                case R.id.machine:
                    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new addmachine()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView navView = findViewById(R.id.nav_view2);


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        profilename = (TextView) headerView.findViewById(R.id.nameprofile);
        mytel = (TextView) headerView.findViewById(R.id.tellprofile);
        myage = (TextView) headerView.findViewById(R.id.vage);
        myarea= (TextView) headerView.findViewById(R.id.varea);
        myaddress = (TextView) headerView.findViewById(R.id.vaddress);
        mypro = (ImageView) headerView.findViewById(R.id.imageView);
        loadprofile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem itemtop) {
        int id = itemtop.getItemId();
        switch (id) {
            case R.id.planting:
                Toast.makeText(this, "Plant", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nofi:
                Toast.makeText(this, "nofi", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(itemtop);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
                        Glide.with(MainActivity.this).load(im).into(mypro);
                        myage.setText(ye+" ปี");
                        profilename.setText(fname+" "+lname);
                        mytel.setText(tel);
                        myarea.setText(ar+" ไร่");
                        myaddress.setText(ad);
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
                SharedPreferences prefs = getSharedPreferences(login.MyPREFERENCES, Activity.MODE_PRIVATE);
                String showidpre = prefs.getString("My_user","NoId");
                params.put("user",showidpre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}
