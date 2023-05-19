package com.project.shakespearesbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.shakespearesbooking.Adapters.ShowsRvAdapter;
import com.project.shakespearesbooking.Database.MyDatabaseHelper;
import com.project.shakespearesbooking.models.ShowModel;
import com.project.shakespearesbooking.models.TicketTypeModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ShowDetailsActivity extends AppCompatActivity {

    Button bookBtn;
    TextView showDetailsTitleTv,showDetailsTimeTv,showDetailsDateTv,showDetailsAddressTv,showDetailsSeatsTv,showDetailsAccTv;
    ImageView backIv,showDetailsIv;
    CheckBox showDetailsCb;
    MyDatabaseHelper dbHelper;

    Boolean isConfirm = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        getSupportActionBar().hide();

        Intent reIntent = getIntent();
        int showPosition = reIntent.getExtras().getInt("showIndex");

        dbHelper = new MyDatabaseHelper(this);

        ShowModel showModel = dbHelper.getShow(this, showPosition+1);

        bookBtn = findViewById(R.id.showDetailsBookBtn);
        backIv = findViewById(R.id.showDetailsBackIv);
        showDetailsTitleTv = findViewById(R.id.showDetailsTitleTv);
        showDetailsTimeTv = findViewById(R.id.showDetailsTimeTv);
        showDetailsDateTv = findViewById(R.id.showDetailsDateTv);
        showDetailsAddressTv = findViewById(R.id.showDetailsAddressTv);
        showDetailsSeatsTv = findViewById(R.id.showDetailsSeatsTv);
        showDetailsAccTv = findViewById(R.id.showDetailsAccTv);
        showDetailsIv = findViewById(R.id.showDetailsIv);
        showDetailsCb = findViewById(R.id.showDetailsCb);

        showDetailsTitleTv.setText(showModel.showTitle);
        showDetailsTimeTv.setText(showModel.dayTime);
        showDetailsDateTv.setText(showModel.date);
        showDetailsAddressTv.setText(showModel.address);

        Picasso.get().load(showModel.img).into(showDetailsIv);
        String seats = "";
        for (TicketTypeModel seat: showModel.ticketTypes){
            seats+=seat.type+": "+seat.count+"\n";
        }
        showDetailsSeatsTv.setText(seats);
        String accs = "";
        for (String acc : showModel.accRestriction){
            accs+=acc+"\n";
        }
        showDetailsAccTv.setText(accs);

        if (showModel.confirmation == "" || showModel.confirmation == null){
            showDetailsCb.setVisibility(View.GONE);
        }else {
            showDetailsCb.setText(showModel.confirmation);
            isConfirm = true;
        }



        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConfirm) {
                    Intent seatsIntent = new Intent(ShowDetailsActivity.this, BookingSeatsActivity.class);
                    seatsIntent.putExtra("showIndex", showPosition + 1);
                    startActivity(seatsIntent);
                }else {
                    if (showDetailsCb.isChecked()) {
                        Intent seatsIntent = new Intent(ShowDetailsActivity.this, BookingSeatsActivity.class);
                        seatsIntent.putExtra("showIndex", showPosition + 1);
                        startActivity(seatsIntent);

                    } else {
                        Toast.makeText(ShowDetailsActivity.this, "You must agree with the confirmation.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}