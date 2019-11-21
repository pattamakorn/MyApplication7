package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class forpuklaideeAdapter extends RecyclerView.Adapter<forpuklaideeAdapter.MyViewHolder> {

    Context formaccontext;
    List<forpuklaidee> mlistformach;
    View view;

    public forpuklaideeAdapter(Context formaccontext, List<forpuklaidee> mlistformach) {
        this.formaccontext = formaccontext;
        this.mlistformach = mlistformach;
    }

    @NonNull
    @Override
    public forpuklaideeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(formaccontext).inflate(R.layout.item_machinepuklaidee,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(view);

        vHolder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(formaccontext,mlistformach.get(vHolder.getAdapterPosition()).getIdmac(), Toast.LENGTH_SHORT).show();
            }
        });

        vHolder.cardofmach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (formaccontext, showtreeformachine.class);
                intent.putExtra("Myidmachine",mlistformach.get(vHolder.getAdapterPosition()).getIdmac());
                formaccontext.startActivity(intent);
                Toast.makeText(formaccontext,mlistformach.get(vHolder.getAdapterPosition()).getIdmac(), Toast.LENGTH_SHORT).show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull forpuklaideeAdapter.MyViewHolder holder, int position) {

        holder.idofmach.setText(mlistformach.get(position).getIdmac());
        holder.addofmach.setText(mlistformach.get(position).getAddressm());

    }

    @Override
    public int getItemCount() {
        return mlistformach.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView idofmach,addofmach;
        private ImageView select;
        private CardView cardofmach;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idofmach = itemView.findViewById(R.id.idmacfortree);
            addofmach = itemView.findViewById(R.id.nameaddressfortree);
            select = itemView.findViewById(R.id.aboutmacfortree);
            cardofmach = itemView.findViewById(R.id.cardmachinefortree);

        }
    }
}
