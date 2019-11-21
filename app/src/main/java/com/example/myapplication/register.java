package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class register extends AppCompatActivity {

    private ImageView selectimgpro;
    private EditText fname,lname,tel,passssss,age,are,addr;
    private Button regis;
    private final int CODE_IMG_GALLERY = 1,CODE_MULTIPLE_IMG_GALLERY = 2;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regis = findViewById(R.id.regis);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        tel = findViewById(R.id.teluser);
        passssss = findViewById(R.id.passregis);
        age = findViewById(R.id.ageregis);
        are = findViewById(R.id.arearegis);
        addr = findViewById(R.id.addressregis);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
                startActivity(new Intent(register.this,login.class));
            }
        });
        init();

        selectimgpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(Intent.createChooser(new Intent().
                        setAction(Intent.ACTION_GET_CONTENT)
                .setType("image/*"),"Select Photo"),PICK_IMAGE_REQUEST);
            }
        });

    }

    private void init(){
        this.selectimgpro = findViewById(R.id.selectprofile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                selectimgpro.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/uploadimgapp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog

                        //Showing toast
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String image = getStringImage(bitmap);
                String nameimg = tel.getText().toString()+"_"+age.getText().toString()+"_"+are.getText().toString();
                Map<String,String> params = new Hashtable<>();
                params.put("image", image);
                params.put("name", nameimg);
                params.put("username", tel.getText().toString());
                params.put("password",passssss.getText().toString());
                params.put("tel", tel.getText().toString());
                params.put("fname",fname.getText().toString());
                params.put("lname", lname.getText().toString());
                params.put("year", age.getText().toString());
                params.put("area", are.getText().toString());
                params.put("address", addr.getText().toString());
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


}
