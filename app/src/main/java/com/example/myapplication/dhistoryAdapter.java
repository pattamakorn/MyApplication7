package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class dhistoryAdapter extends RecyclerView.Adapter<dhistoryAdapter.MyViewHolder> {

    Context hiscontext;
    List<dhistory> mlisthistory;
    View view;

    public dhistoryAdapter(Context hiscontext, List<dhistory> mlisthistory) {
        this.hiscontext = hiscontext;
        this.mlisthistory = mlisthistory;
    }

    @NonNull
    @Override
    public dhistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(hiscontext).inflate(R.layout.item_history,parent,false);
        MyViewHolder vHolder = new MyViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull dhistoryAdapter.MyViewHolder holder, int position) {

        holder.hhnametree.setText(mlisthistory.get(position).getHnametree());
        holder.hhweight.setText(mlisthistory.get(position).getHweight());
        holder.hhdate.setText(mlisthistory.get(position).getHdatet());
        holder.hhstatus.setText(mlisthistory.get(position).getHstatus());
        Glide.with(view.getContext()).load(mlisthistory.get(position).getHimg()).into(holder.hhpictree);

    }

    @Override
    public int getItemCount() {
        return mlisthistory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView hhnametree,hhweight,hhdate,hhstatus;
        private ImageView hhpictree;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hhnametree = itemView.findViewById(R.id.hisname);
            hhweight = itemView.findViewById(R.id.hisweight);
            hhdate = itemView.findViewById(R.id.hisdate);
            hhstatus = itemView.findViewById(R.id.hisstatus);
            hhpictree = itemView.findViewById(R.id.hisimg);
        }
    }
}
