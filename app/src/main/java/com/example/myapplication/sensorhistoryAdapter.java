package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class sensorhistoryAdapter extends RecyclerView.Adapter<sensorhistoryAdapter.MyViewHolder> {
    Context sensorcontext;
    List<sensorhistory> mlisthistorysen;
    View view;

    public sensorhistoryAdapter(Context sensorcontext, List<sensorhistory> mlisthistorysen) {
        this.sensorcontext = sensorcontext;
        this.mlisthistorysen = mlisthistorysen;
    }

    @NonNull
    @Override
    public sensorhistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(sensorcontext).inflate(R.layout.item_sensorhistory,parent,false);
        MyViewHolder vHolder = new MyViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull sensorhistoryAdapter.MyViewHolder holder, int position) {

        holder.lmoist.setText(mlisthistorysen.get(position).getMoist()+"%");
        holder.lph.setText(mlisthistorysen.get(position).getPh());
        holder.llight.setText(mlisthistorysen.get(position).getLight());
        holder.dddate.setText(mlisthistorysen.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mlisthistorysen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView lmoist,lph,llight,dddate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dddate = itemView.findViewById(R.id.date);
            lmoist = itemView.findViewById(R.id.lmoist);
            lph = itemView.findViewById(R.id.lph);
            llight = itemView.findViewById(R.id.llight);

        }
    }
}
