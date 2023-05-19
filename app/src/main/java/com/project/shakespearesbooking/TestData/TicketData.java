package com.project.shakespearesbooking.TestData;

import com.project.shakespearesbooking.models.SeatModel;
import com.project.shakespearesbooking.models.ShowModel;
import com.project.shakespearesbooking.models.TicketModel;
import com.project.shakespearesbooking.models.TicketTypeModel;

import java.util.ArrayList;

public class TicketData {
    public ArrayList<TicketModel> tickets = new ArrayList<>();
    public ArrayList<ShowModel> shows = new ArrayList<>();
    public ShowData showData = new ShowData();
    public void addData(){
        showData.addData();
        shows = showData.shows;
        TicketModel tm1 = new TicketModel();
        tm1.id = 1;
        tm1.showModel = shows.get(2);
        SeatModel t1t1 = new SeatModel();
        t1t1.price = 35;
        t1t1.seatType = "Seated";
        t1t1.seatCount = 2;
        tm1.seatB.add(t1t1);
        SeatModel t1t2 = new SeatModel();
        t1t2.price = 30;
        t1t2.seatType = "Standing";
        t1t2.seatCount = 3;
        tm1.seatB.add(t1t2);
        tickets.add(tm1);
        tickets.add(tm1);
        tickets.add(tm1);
    }
}
