package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    private EditText user,passs;
    private Button login;
    private TextView create,forget;
    private String URL_LOGIN = "http://203.154.83.137/puklaidee/login2.php";
    public static final String MyPREFERENCES = "Setting" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        user = findViewById(R.id.log_name_et);
        passs = findViewById(R.id.log_password_et);
        login = findViewById(R.id.login);
        create = findViewById(R.id.reg_page);
        forget = findViewById(R.id.forgetpass);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
        String showidpre = prefs.getString("My_user","NoId");
        if (showidpre != "NoId"){
            startActivity(new Intent(this,MainActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginphp();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,register.class));
            }
        });


    }

    private void loginphp(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_LOGIN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")){
                                for (int i = 0;i < jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String musername = object.getString("username");
                                    String mpassword = object.getString("password");
                                    String fname = object.getString("fname");
                                    String lname = object.getString("lname");
                                    String yearOld = object.getString("yearold");
                                    String tel = object.getString("tel");
                                    String area = object.getString("area");
                                    String address = object.getString("address");
                                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
                                    editor.putString("My_user",musername);
                                    editor.apply();

                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    intent.putExtra("usernamee", musername);
                                    startActivity(intent);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login.this, "ไม่สามารถเข้าสู่ระบบได้กรุณาเช็ค \n Username\n Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user",user.getText().toString());
                params.put("password",passs.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
