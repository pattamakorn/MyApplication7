package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class dividenddetailAdapter extends RecyclerView.Adapter<dividenddetailAdapter.MyViewHolder> {

    Context dividendcontext;
    List<dividenddetail> mlistdividend;
    View view;

    public dividenddetailAdapter(Context dividendcontext, List<dividenddetail> mlistdividend) {
        this.dividendcontext = dividendcontext;
        this.mlistdividend = mlistdividend;
    }

    @NonNull
    @Override
    public dividenddetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(dividendcontext).inflate(R.layout.item_dividend,parent,false);
        MyViewHolder vHolder = new MyViewHolder(view);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull dividenddetailAdapter.MyViewHolder holder, int position) {

        holder.moneydi.setText(mlistdividend.get(position).getMoney());
        holder.datedi.setText(mlistdividend.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mlistdividend.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView datedi,moneydi;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            datedi = itemView.findViewById(R.id.datedi);
            moneydi = itemView.findViewById(R.id.moneydi);

        }
    }
}
