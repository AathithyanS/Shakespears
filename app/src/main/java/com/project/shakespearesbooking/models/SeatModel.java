package com.project.shakespearesbooking.models;

public class SeatModel {
    public int id;
    public String seatType;
    public int seatCount;
    public int price;

    public int getId() {
        return id;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
