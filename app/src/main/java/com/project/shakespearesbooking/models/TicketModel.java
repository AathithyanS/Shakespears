package com.project.shakespearesbooking.models;

import java.util.ArrayList;

public class TicketModel {
    public int id;
    public String note, fullName;
    public String userId;
    public long RefNo;
    public ShowModel showModel;
    public ArrayList<SeatModel> seatB = new ArrayList();

}
