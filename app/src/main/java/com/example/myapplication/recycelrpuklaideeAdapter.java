package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.Map;

public class recycelrpuklaideeAdapter extends RecyclerView.Adapter<recycelrpuklaideeAdapter.MyViewHolder> {

    Context rmcontext;
    List<recycelrpuklaidee> mlistretree;
    View view;
    puklaidee getpuk;
    String dd;
    public String mid;


    public String treeid,myiot;

    public recycelrpuklaideeAdapter(Context rmcontext, List<recycelrpuklaidee> mlistretree) {
        this.rmcontext = rmcontext;
        this.mlistretree = mlistretree;
    }

    @NonNull
    @Override
    public recycelrpuklaideeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(rmcontext).inflate(R.layout.item_puklaidee,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(view);

        vHolder.rrstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mid = mlistretree.get(vHolder.getAdapterPosition()).getIduserr();
                treeid = mlistretree.get(vHolder.getAdapterPosition()).getIdtree();
                myiot = mlistretree.get(vHolder.getAdapterPosition()).getIdmachineselect();
                Intent intent = new Intent (rmcontext, startplant.class);
                intent.putExtra("Myidtree",mlistretree.get(vHolder.getAdapterPosition()).getIdtree());
                rmcontext.startActivity(intent);
                Toast.makeText(rmcontext,mlistretree.get(vHolder.getAdapterPosition()).getIdmachineselect(), Toast.LENGTH_SHORT).show();
                insertstart();
            }
        });

        vHolder.rrmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(rmcontext,mlistretree.get(vHolder.getAdapterPosition()).getIdmachineselect(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (rmcontext, moreplant.class);
                intent.putExtra("Myidtree",mlistretree.get(vHolder.getAdapterPosition()).getIdtree());
                rmcontext.startActivity(intent);
                //Toast.makeText(rmcontext,mlistretree.get(vHolder.getAdapterPosition()).getIdtree(), Toast.LENGTH_SHORT).show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recycelrpuklaideeAdapter.MyViewHolder holder, int position) {
        holder.rrre.setText(mlistretree.get(position).getNamettre());
        Glide.with(view.getContext()).load(mlistretree.get(position).getTreepic()).into(holder.rrtree);

    }

    @Override
    public int getItemCount() {
        return mlistretree.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView rrtree;
        private Button rrstart,rrmore;
        private TextView rrmoist,rrph,rrlight,rrre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rrtree = itemView.findViewById(R.id.idpicpuklaidee);
            rrstart = itemView.findViewById(R.id.startplant);
            rrmore = itemView.findViewById(R.id.more);
            rrre = itemView.findViewById(R.id.remembered);

        }
    }

    public void insertstart(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/puklaidee/startplant.php", new Response.Listener<String>() {
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
                params.put("idtree",treeid);
                params.put("inuser",mid);
                params.put("iot",myiot);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(rmcontext);
        requestQueue.add(stringRequest);
    }

}
