package com.project.shakespearesbooking.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shakespearesbooking.R;
import com.project.shakespearesbooking.TicketsDetailsActivity;
import com.project.shakespearesbooking.models.SeatModel;
import com.project.shakespearesbooking.models.TicketModel;
import com.project.shakespearesbooking.models.TicketTypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TicketRvAdapter extends RecyclerView.Adapter<TicketRvAdapter.MyViewHolder> {

    ArrayList<TicketModel> tickets;
    Context context;

    public TicketRvAdapter(ArrayList<TicketModel> tickets, Context context){
        this.tickets = tickets;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TicketModel ticket = tickets.get(position);
        holder.ticketTitleTv.setText(ticket.showModel.showTitle);
        holder.ticketDateTv.setText(ticket.showModel.date);
        holder.ticketTimeTv.setText(ticket.showModel.dayTime);
        holder.ticketRefTv.setText(ticket.RefNo+"");
        StringBuilder seats = new StringBuilder();
        for (SeatModel seat : ticket.seatB ){
            seats.append(seat.seatType).append(": ").append(seat.seatCount).append("\n");
        }
        holder.ticketSeatsTv.setText(seats.toString());
        Picasso.get().load(ticket.showModel.img).into(holder.ticketIv);
        holder.ticketItemCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookingDetails = new Intent(context, TicketsDetailsActivity.class);
                bookingDetails.putExtra("ticketId", ticket.id);
                context.startActivity(bookingDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ticketTitleTv, ticketDateTv, ticketTimeTv, ticketSeatsTv, ticketRefTv;
        public ConstraintLayout ticketItemCl;
        public ImageView ticketIv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketTitleTv = itemView.findViewById(R.id.ticketTitleTv);
            ticketDateTv = itemView.findViewById(R.id.ticketDateTv);
            ticketTimeTv = itemView.findViewById(R.id.ticketTimeTv);
            ticketSeatsTv = itemView.findViewById(R.id.ticketSeatsTv);
            ticketRefTv = itemView.findViewById(R.id.ticketRefTv);
            ticketIv = itemView.findViewById(R.id.ticketIv);
            ticketItemCl = itemView.findViewById(R.id.ticketItemCl);
        }
    }
}
