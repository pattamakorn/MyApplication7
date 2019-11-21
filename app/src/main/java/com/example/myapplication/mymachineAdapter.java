package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class mymachineAdapter extends RecyclerView.Adapter<mymachineAdapter.MyViewHolder> {

    Context maccontext;
    List<mymachine> mlistmac;
    View view;

    public mymachineAdapter(Context maccontext, List<mymachine> mlistmac) {
        this.maccontext = maccontext;
        this.mlistmac = mlistmac;
    }

    @NonNull
    @Override
    public mymachineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(maccontext).inflate(R.layout.item_machine,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(view);

        vHolder.gotomore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (maccontext, aboutMachine.class);
                intent.putExtra("Myidmachine",mlistmac.get(vHolder.getAdapterPosition()).getIdmac());
                intent.putExtra("Myaddress",mlistmac.get(vHolder.getAdapterPosition()).getNameaddress());
                maccontext.startActivity(intent);
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull mymachineAdapter.MyViewHolder holder, int position) {

        holder.idmaccc.setText(mlistmac.get(position).getIdmac());
        holder.nameaddress.setText(mlistmac.get(position).getNameaddress());

    }

    @Override
    public int getItemCount() {
        return mlistmac.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView idmaccc,nameaddress;
        private CardView gotomore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idmaccc = itemView.findViewById(R.id.idmac);
            nameaddress = itemView.findViewById(R.id.nameaddress);
            gotomore = itemView.findViewById(R.id.cardmachine);

        }
    }
}
