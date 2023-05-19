package com.project.shakespearesbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.shakespearesbooking.Database.MyDatabaseHelper;
import com.project.shakespearesbooking.models.SeatModel;
import com.project.shakespearesbooking.models.TicketModel;
import com.squareup.picasso.Picasso;

public class TicketsDetailsActivity extends AppCompatActivity {

    ImageView backIv, ticketDetailsIv;
    TextView ticketDetailsTitleTv, ticketsDetailRefTv, ticketsDetailDateTv, ticketsDetailTimeTv, ticketsDetailAddressTv,ticketsDetailSeatsTv;
    Button ticketsDetailCancelBtn;

    MyDatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_details);

        getSupportActionBar().hide();

        backIv = findViewById(R.id.ticketDetailsBackIv);
        ticketDetailsIv = findViewById(R.id.ticketDetailsIv);
        ticketDetailsTitleTv = findViewById(R.id.ticketDetailsTitleTv);
        ticketsDetailRefTv = findViewById(R.id.ticketsDetailRefTv);
        ticketsDetailDateTv = findViewById(R.id.ticketsDetailDateTv);
        ticketsDetailTimeTv = findViewById(R.id.ticketsDetailTimeTv);
        ticketsDetailAddressTv = findViewById(R.id.ticketsDetailAddressTv);
        ticketsDetailSeatsTv = findViewById(R.id.ticketsDetailSeatsTv);
        ticketsDetailCancelBtn = findViewById(R.id.ticketsDetailCancelBtn);

        Intent intent = getIntent();
        int seatId = intent.getExtras().getInt("ticketId");


        dbHelper = new MyDatabaseHelper(this);

        TicketModel ticket = dbHelper.getTicket(this, seatId);

        Picasso.get().load(ticket.showModel.img).into(ticketDetailsIv);
        ticketDetailsTitleTv.setText(ticket.showModel.showTitle);
        ticketsDetailRefTv.setText(String.valueOf(ticket.RefNo));
        ticketsDetailDateTv.setText(ticket.showModel.date);
        ticketsDetailTimeTv.setText(ticket.showModel.dayTime);
        ticketsDetailAddressTv.setText(ticket.showModel.address);
        String seats="";
        for (SeatModel seat: ticket.seatB){
            seats += seat.seatType+": "+seat.seatCount+"\n";
        }
        ticketsDetailSeatsTv.setText(seats);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ticketsDetailCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteTicket(ticket, getApplicationContext());
                finishAffinity();
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });



    }
}