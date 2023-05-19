package com.project.shakespearesbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.project.shakespearesbooking.Adapters.SeatsRvAdapter;
import com.project.shakespearesbooking.Database.MyDatabaseHelper;
import com.project.shakespearesbooking.TestData.ShowData;
import com.project.shakespearesbooking.models.SeatModel;
import com.project.shakespearesbooking.models.ShowModel;
import com.project.shakespearesbooking.models.TicketModel;
import com.project.shakespearesbooking.models.TicketTypeModel;

import java.util.ArrayList;

public class BookingSeatsActivity extends AppCompatActivity {

    private RecyclerView seatsRv;
    private Button bookBtn;
    private ImageView backIv;
    private TextView totalPrice;

    private SeatsRvAdapter seatsRvAdapter;
    ShowModel currentShow;

    MyDatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_seats);

        getSupportActionBar().hide();

        backIv = findViewById(R.id.seatBookBackIv);
        seatsRv = findViewById(R.id.seatBookRv);
        totalPrice = findViewById(R.id.seatBookTotalTv);
        bookBtn = findViewById(R.id.seatBookBtn);

        dbHelper = new MyDatabaseHelper(this);


        Intent intent = getIntent();
        int showPosition = intent.getExtras().getInt("showIndex");

        currentShow =dbHelper.getShow(this,showPosition);
        ArrayList<TicketTypeModel> seats = currentShow.ticketTypes;

        seatsRvAdapter = new SeatsRvAdapter(seats, this, totalPrice);

        seatsRv.setLayoutManager(new LinearLayoutManager(this));
        seatsRv.setAdapter(seatsRvAdapter);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalCount = 0;
                for (SeatModel tm : seatsRvAdapter.selectedSeats){
                    totalCount += tm.seatCount;
                }
                if (totalCount > 0) {
                    openNoteDialog();
                }else {
                    Toast.makeText(BookingSeatsActivity.this, "Please add your seats first.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void openNoteDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.note_booking);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.show();

        TextInputEditText textInputLayout = dialog.findViewById(R.id.alertNoteEt);
        Button cntBtn = dialog.findViewById(R.id.alertNoteBtn);
        cntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTv = textInputLayout.getText().toString();
                if (!noteTv.isEmpty()){
                    openNameDialog(noteTv);
                    dialog.dismiss();
                }else {
                    Toast.makeText(BookingSeatsActivity.this, "Note can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void openNameDialog(String note) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.name_booking);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        TextInputEditText textInputLayout = dialog.findViewById(R.id.alertNameEt);
        Button finishBtn = dialog.findViewById(R.id.alertNameBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textInputLayout.getText().toString();
                if (!name.isEmpty()) {
                    TicketModel ticketInsert = new TicketModel();
                    ticketInsert.note = note;
                    ticketInsert.userId = email;
                    ticketInsert.fullName = name;
                    ticketInsert.showModel = currentShow;
                    ticketInsert.seatB = seatsRvAdapter.selectedSeats;
                    dbHelper.insertTicket(ticketInsert);
                    dialog.dismiss();
                    openSuccessMessage();
                }else{
                    Toast.makeText(BookingSeatsActivity.this, "Name can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openSuccessMessage() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.success_booking);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Button closeBtn = dialog.findViewById(R.id.successBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intetMain = new Intent(BookingSeatsActivity.this, MainActivity.class);
                startActivity(intetMain);
                finishAffinity();
            }
        });
    }
}