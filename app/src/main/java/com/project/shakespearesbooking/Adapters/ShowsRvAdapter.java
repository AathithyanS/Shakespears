package com.project.shakespearesbooking.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shakespearesbooking.R;
import com.project.shakespearesbooking.ShowDetailsActivity;
import com.project.shakespearesbooking.models.ShowModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowsRvAdapter extends RecyclerView.Adapter<ShowsRvAdapter.MyViewHolder> {

    private ArrayList<ShowModel> shows;
    private Context context;

    public ShowsRvAdapter(ArrayList<ShowModel> shows, Context context){
        this.shows = shows;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ShowModel show = shows.get(position);
        holder.nameTv.setText(show.showTitle);
        Picasso.get().load(show.img).into(holder.showIv);
        holder.showIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("showIndex", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public ImageView showIv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.showItemTv);
            showIv = itemView.findViewById(R.id.showItemIv);
        }
    }

}
