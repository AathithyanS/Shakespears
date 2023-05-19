package com.project.shakespearesbooking;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.shakespearesbooking.Adapters.TicketRvAdapter;
import com.project.shakespearesbooking.Database.MyDatabaseHelper;
import com.project.shakespearesbooking.TestData.TicketData;
import com.project.shakespearesbooking.models.TicketModel;

import java.util.ArrayList;

public class TicketFragment extends Fragment {

    private RecyclerView ticketRv;
    private TextView ticketNotFoundTv;
    private TicketRvAdapter ticketRvAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ticket, container, false);

        TicketData td = new TicketData();
        td.addData();

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        ArrayList<TicketModel> tickets = dbHelper.getAllTickets(getContext(), email);
        ticketRvAdapter = new TicketRvAdapter(tickets, getActivity());

        ticketRv = rootView.findViewById(R.id.bookingRv);
        ticketNotFoundTv = rootView.findViewById(R.id.ticketNotFoundTv);

        ticketRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ticketRv.setAdapter(ticketRvAdapter);

        if (tickets.size() > 0){
            ticketNotFoundTv.setVisibility(View.GONE);
        }else {
            ticketNotFoundTv.setVisibility(View.VISIBLE);
        }


        return rootView;
    }
}