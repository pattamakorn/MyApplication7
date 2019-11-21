package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.MyViewHolder> {

    Context mcontext;
    List<news> mlistnews;
    View view;

    public newsAdapter(Context mcontext, List<news> mlistnews) {
        this.mcontext = mcontext;
        this.mlistnews = mlistnews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.item_news,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(view);



        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (mcontext, morenews.class);
                intent.putExtra("MyValue",mlistnews.get(vHolder.getAdapterPosition()).getIdnews());
                mcontext.startActivity(intent);

                Toast.makeText(mcontext,mlistnews.get(vHolder.getAdapterPosition()).getIdnews(),
                        Toast.LENGTH_SHORT).show();



            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dateday.setText(mlistnews.get(position).getDate());
        holder.news.setText(mlistnews.get(position).getValuenews());
        Glide.with(view.getContext()).load(mlistnews.get(position).getImgpost()).into(holder.imgpost);

    }

    @Override
    public int getItemCount() {
        return mlistnews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView dateday,news;
        private ImageView imgpost;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateday = itemView.findViewById(R.id.datenewspost);
            news = itemView.findViewById(R.id.valuenews);
            imgpost = itemView.findViewById(R.id.imgnews);
            cardView = itemView.findViewById(R.id.cardnews);

        }
    }
}
