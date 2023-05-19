package com.project.shakespearesbooking.models;

import java.util.ArrayList;

public class ShowModel {

    public int id;
    public String showTitle;
    public String dayTime;

    public String img;
    public String date;
    public String address;
    public String confirmation = null;
    public ArrayList<String> accRestriction = new ArrayList<>();
    public ArrayList<TicketTypeModel> ticketTypes = new ArrayList<>();

}
