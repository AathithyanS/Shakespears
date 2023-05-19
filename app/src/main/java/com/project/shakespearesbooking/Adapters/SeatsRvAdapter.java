package com.project.shakespearesbooking.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shakespearesbooking.R;
import com.project.shakespearesbooking.models.SeatModel;
import com.project.shakespearesbooking.models.TicketTypeModel;

import java.util.ArrayList;

public class SeatsRvAdapter extends RecyclerView.Adapter<SeatsRvAdapter.MyViewHolder> {

    private ArrayList<TicketTypeModel> seats;
    private Context context;
    private TextView totalPrice;
    final int[] tPrice = {0};
    public ArrayList<SeatModel> selectedSeats = new ArrayList<>();

    public SeatsRvAdapter(ArrayList<TicketTypeModel> seats, Context context, TextView totalPrice){
        this.seats = seats;
        this.totalPrice = totalPrice;
    }

    @NonNull
    @Override
    public SeatsRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seat_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatsRvAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TicketTypeModel seat = seats.get(position);
        SeatModel selectedSeat = new SeatModel();
        holder.seatSelectTypeTv.setText(seat.type);
        holder.seatSelectPriceTv.setText("£"+seat.price);
        holder.seatSelectRemainingTv.setText(seat.count+"");
        selectedSeat.seatType = seat.type;
        selectedSeat.price = seat.price;
        selectedSeat.seatCount = 0;

        selectedSeats.add(selectedSeat);

        holder.seatSelectIncreaseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.seatSelectCountTv.getText().toString());
                count++;
                if (count >= 0 && count <= seat.count){
                    holder.seatSelectCountTv.setText(count+"");
                    selectedSeats.get(position).seatCount = count;
                    tPrice[0] += seat.price;
                    totalPrice.setText("£"+tPrice[0]);
                }
            }
        });
        holder.seatSelectDecreaseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.seatSelectCountTv.getText().toString());
                count--;
                if (count >= 0 && count <= seat.count){
                    holder.seatSelectCountTv.setText(count+"");
                    selectedSeats.get(position).seatCount = count;
                    tPrice[0] -= seat.price;
                    totalPrice.setText("£"+tPrice[0]);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView seatSelectTypeTv, seatSelectRemainingTv, seatSelectPriceTv, seatSelectCountTv;
        ImageView seatSelectDecreaseIv, seatSelectIncreaseIv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            seatSelectTypeTv = itemView.findViewById(R.id.seatSelectTypeTv);
            seatSelectRemainingTv = itemView.findViewById(R.id.seatSelectRemainingTv);
            seatSelectPriceTv = itemView.findViewById(R.id.seatSelectPriceTv);
            seatSelectCountTv = itemView.findViewById(R.id.seatSelectCountTv);
            seatSelectDecreaseIv = itemView.findViewById(R.id.seatSelectDecreaseIv);
            seatSelectIncreaseIv = itemView.findViewById(R.id.seatSelectIncreaseIv);

        }
    }
}
