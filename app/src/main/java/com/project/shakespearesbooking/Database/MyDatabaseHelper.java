package com.project.shakespearesbooking.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.project.shakespearesbooking.models.SeatModel;
import com.project.shakespearesbooking.models.ShowModel;
import com.project.shakespearesbooking.models.TicketModel;
import com.project.shakespearesbooking.models.TicketTypeModel;
import com.project.shakespearesbooking.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;
    Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the necessary tables
        db.execSQL("CREATE TABLE Show (" +
                "id INTEGER PRIMARY KEY," +
                "showTitle TEXT," +
                "dayTime TEXT," +
                "img TEXT," +
                "date TEXT," +
                "address TEXT," +
                "confirmation TEXT," +
                "accRestriction TEXT" +
                ")");
        db.execSQL("CREATE TABLE TicketType (" +
                "id INTEGER PRIMARY KEY," +
                "show_id INTEGER," +
                "type TEXT," +
                "count INTEGER," +
                "price INTEGER," +
                "FOREIGN KEY (show_id) REFERENCES Show(id)" +
                ")");
        db.execSQL("CREATE TABLE Ticket (" +
                "id INTEGER PRIMARY KEY," +
                "show_id INTEGER," +
                "user_id TEXT," +
                "refNo LONG,"+
                "note LONG,"+
                "full_name LONG,"+
                "FOREIGN KEY (show_id) REFERENCES Show(id)," +
                "FOREIGN KEY (user_id) REFERENCES User(email)" +
                ")");
        db.execSQL("CREATE TABLE Seat (" +
                "id INTEGER PRIMARY KEY," +
                "ticket_id INTEGER,"+
                "seatType TEXT," +
                "seatCount INTEGER," +
                "price INTEGER," +
                "FOREIGN KEY (ticket_id) REFERENCES Ticket(id)" +
                ")");
        db.execSQL("CREATE TABLE User (" +
                "email TEXT PRIMARY KEY," +
                "username TEXT,"+
                "password TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle any necessary database upgrades
    }

    @SuppressLint("Range")
    public ArrayList<ShowModel> getAllShows(Context context) {
        ArrayList<ShowModel> shows = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Show", null);
        if (cursor.moveToFirst()) {
            do {
                ShowModel show = new ShowModel();
                show.id = cursor.getInt(cursor.getColumnIndex("id"));
                show.showTitle = cursor.getString(cursor.getColumnIndex("showTitle"));
                show.dayTime = cursor.getString(cursor.getColumnIndex("dayTime"));
                show.img = cursor.getString(cursor.getColumnIndex("img"));
                show.date = cursor.getString(cursor.getColumnIndex("date"));
                show.address = cursor.getString(cursor.getColumnIndex("address"));
                show.confirmation = cursor.getString(cursor.getColumnIndex("confirmation"));

                String accRestrictionString = cursor.getString(cursor.getColumnIndex("accRestriction"));
                if (accRestrictionString != null) {
                    String[] accRestrictions = accRestrictionString.split(",");
                    for (String accRestriction : accRestrictions) {
                        show.accRestriction.add(accRestriction);
                    }
                }

                // Retrieve ticket types for this show
                int showId = cursor.getInt(cursor.getColumnIndex("id"));
                Cursor ticketTypeCursor = db.rawQuery("SELECT * FROM TicketType WHERE show_id=?", new String[]{String.valueOf(showId)});
                if (ticketTypeCursor.moveToFirst()) {
                    do {
                        TicketTypeModel ticketType = new TicketTypeModel();
                        ticketType.type = ticketTypeCursor.getString(ticketTypeCursor.getColumnIndex("type"));
                        ticketType.count = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("count"));
                        ticketType.price = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("price"));

                        show.ticketTypes.add(ticketType);
                    } while (ticketTypeCursor.moveToNext());
                }
                ticketTypeCursor.close();

                shows.add(show);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return shows;
    }

    @SuppressLint("Range")
    public ArrayList<ShowModel> getSearchShows(Context context,String key) {
        ArrayList<ShowModel> shows = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Show WHERE showTitle LIKE ?", new String[]{String.valueOf("%"+key+"%")});
        if (cursor.moveToFirst()) {
            do {
                ShowModel show = new ShowModel();
                show.id = cursor.getInt(cursor.getColumnIndex("id"));
                show.showTitle = cursor.getString(cursor.getColumnIndex("showTitle"));
                show.dayTime = cursor.getString(cursor.getColumnIndex("dayTime"));
                show.img = cursor.getString(cursor.getColumnIndex("img"));
                show.date = cursor.getString(cursor.getColumnIndex("date"));
                show.address = cursor.getString(cursor.getColumnIndex("address"));
                show.confirmation = cursor.getString(cursor.getColumnIndex("confirmation"));

                String accRestrictionString = cursor.getString(cursor.getColumnIndex("accRestriction"));
                if (accRestrictionString != null) {
                    String[] accRestrictions = accRestrictionString.split(",");
                    for (String accRestriction : accRestrictions) {
                        show.accRestriction.add(accRestriction);
                    }
                }

                // Retrieve ticket types for this show
                int showId = cursor.getInt(cursor.getColumnIndex("id"));
                Cursor ticketTypeCursor = db.rawQuery("SELECT * FROM TicketType WHERE show_id=?", new String[]{String.valueOf(showId)});
                if (ticketTypeCursor.moveToFirst()) {
                    do {
                        TicketTypeModel ticketType = new TicketTypeModel();
                        ticketType.type = ticketTypeCursor.getString(ticketTypeCursor.getColumnIndex("type"));
                        ticketType.count = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("count"));
                        ticketType.price = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("price"));

                        show.ticketTypes.add(ticketType);
                    } while (ticketTypeCursor.moveToNext());
                }
                ticketTypeCursor.close();

                shows.add(show);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return shows;
    }

    @SuppressLint("Range")
    public ArrayList<TicketModel> getAllTickets(Context context, String userid) {
        ArrayList<TicketModel> tickets = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Ticket WHERE user_id = ? ORDER BY id DESC",  new String[]{String.valueOf(userid)});
        if (cursor.moveToFirst()) {
            do {
                TicketModel ticket = new TicketModel();
                ticket.id = cursor.getInt(cursor.getColumnIndex("id"));
                int show_id = cursor.getInt(cursor.getColumnIndex("show_id"));
                ticket.note = cursor.getString(cursor.getColumnIndex("note"));
                ticket.fullName = cursor.getString(cursor.getColumnIndex("full_name"));
                ticket.RefNo = cursor.getLong(cursor.getColumnIndex("refNo"));
                ticket.showModel = getShow(context,show_id);
                ticket.seatB = getTicketSeats(ticket.id);
                tickets.add(ticket);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tickets;
    }

    @SuppressLint("Range")
    public ArrayList<SeatModel> getTicketSeats(int ticket_id){
        ArrayList<SeatModel> bookedSeats = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor seatCursor = db.rawQuery("SELECT * FROM Seat WHERE ticket_id=?", new String[]{String.valueOf(ticket_id)});
        if (seatCursor.moveToFirst()) {
            do {
                SeatModel seat = new SeatModel();
                seat.id = seatCursor.getInt(seatCursor.getColumnIndex("id"));
                seat.seatCount = seatCursor.getInt(seatCursor.getColumnIndex("seatCount"));
                seat.seatType = seatCursor.getString(seatCursor.getColumnIndex("seatType"));
                seat.price = seatCursor.getInt(seatCursor.getColumnIndex("price"));

                bookedSeats.add(seat);
            } while (seatCursor.moveToNext());
        }
        seatCursor.close();
        db.close();
        return bookedSeats;
    }

    @SuppressLint("Range")
    public ShowModel getShow(Context context, int sId) {
        ShowModel show = new ShowModel();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Show WHERE id = ?",  new String[]{String.valueOf(sId)});
        if (cursor.moveToFirst()) {
            do {
                show.id = cursor.getInt(cursor.getColumnIndex("id"));
                show.showTitle = cursor.getString(cursor.getColumnIndex("showTitle"));
                show.dayTime = cursor.getString(cursor.getColumnIndex("dayTime"));
                show.img = cursor.getString(cursor.getColumnIndex("img"));
                show.date = cursor.getString(cursor.getColumnIndex("date"));
                show.address = cursor.getString(cursor.getColumnIndex("address"));
                show.confirmation = cursor.getString(cursor.getColumnIndex("confirmation"));

                String accRestrictionString = cursor.getString(cursor.getColumnIndex("accRestriction"));
                if (accRestrictionString != null) {
                    String[] accRestrictions = accRestrictionString.split(",");
                    for (String accRestriction : accRestrictions) {
                        show.accRestriction.add(accRestriction);
                    }
                }

                // Retrieve ticket types for this show
                int showId = cursor.getInt(cursor.getColumnIndex("id"));
                Cursor ticketTypeCursor = db.rawQuery("SELECT * FROM TicketType WHERE show_id=?", new String[]{String.valueOf(showId)});
                if (ticketTypeCursor.moveToFirst()) {
                    do {
                        TicketTypeModel ticketType = new TicketTypeModel();
                        ticketType.id = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("id"));
                        ticketType.type = ticketTypeCursor.getString(ticketTypeCursor.getColumnIndex("type"));
                        ticketType.count = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("count"));
                        ticketType.price = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("price"));

                        show.ticketTypes.add(ticketType);
                    } while (ticketTypeCursor.moveToNext());
                }
                ticketTypeCursor.close();
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return show;
    }

    @SuppressLint("Range")
    public ShowModel getShow(Context context, int sId, SQLiteDatabase db) {
        ShowModel show = new ShowModel();

        Cursor cursor = db.rawQuery("SELECT * FROM Show WHERE id = ?",  new String[]{String.valueOf(sId)});
        if (cursor.moveToFirst()) {
            do {
                show.id = cursor.getInt(cursor.getColumnIndex("id"));
                show.showTitle = cursor.getString(cursor.getColumnIndex("showTitle"));
                show.dayTime = cursor.getString(cursor.getColumnIndex("dayTime"));
                show.img = cursor.getString(cursor.getColumnIndex("img"));
                show.date = cursor.getString(cursor.getColumnIndex("date"));
                show.address = cursor.getString(cursor.getColumnIndex("address"));
                show.confirmation = cursor.getString(cursor.getColumnIndex("confirmation"));

                String accRestrictionString = cursor.getString(cursor.getColumnIndex("accRestriction"));
                if (accRestrictionString != null) {
                    String[] accRestrictions = accRestrictionString.split(",");
                    for (String accRestriction : accRestrictions) {
                        show.accRestriction.add(accRestriction);
                    }
                }

                // Retrieve ticket types for this show
                int showId = cursor.getInt(cursor.getColumnIndex("id"));
                Cursor ticketTypeCursor = db.rawQuery("SELECT * FROM TicketType WHERE show_id=?", new String[]{String.valueOf(showId)});
                if (ticketTypeCursor.moveToFirst()) {
                    do {
                        TicketTypeModel ticketType = new TicketTypeModel();
                        ticketType.id = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("id"));
                        ticketType.type = ticketTypeCursor.getString(ticketTypeCursor.getColumnIndex("type"));
                        ticketType.count = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("count"));
                        ticketType.price = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("price"));

                        show.ticketTypes.add(ticketType);
                    } while (ticketTypeCursor.moveToNext());
                }
                ticketTypeCursor.close();
            } while (cursor.moveToNext());
        }

        cursor.close();

        return show;
    }


    @SuppressLint("Range")
    public Boolean isLogin(Context context, UserModel user) {

        Boolean userLog = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE email = ? AND password = ?",  new String[]{String.valueOf(user.email), String.valueOf(user.password)});

        if (cursor.moveToFirst()) {
            user.email = cursor.getString(cursor.getColumnIndex("email"));
            user.password = cursor.getString(cursor.getColumnIndex("username"));
            user.username = cursor.getString(cursor.getColumnIndex("password"));
            userLog = true;
        }

        cursor.close();
        db.close();

        return userLog;
    }

    @SuppressLint("Range")
    public UserModel getUser(Context context, String email) {

        UserModel user = new UserModel();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE email = ?",  new String[]{String.valueOf(email)});

        if (cursor.moveToFirst()) {
            user.email = cursor.getString(cursor.getColumnIndex("email"));
            user.password = cursor.getString(cursor.getColumnIndex("username"));
            user.username = cursor.getString(cursor.getColumnIndex("password"));
        }

        cursor.close();
        db.close();

        return user;
    }

    @SuppressLint("Range")
    public TicketModel getTicket(Context context, int tId) {
        TicketModel ticket = new TicketModel();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Ticket WHERE id = ?",  new String[]{String.valueOf(tId)});
        if (cursor.moveToFirst()) {
            do {
                ticket.id = cursor.getInt(cursor.getColumnIndex("id"));
                ticket.note = cursor.getString(cursor.getColumnIndex("note"));
                ticket.fullName = cursor.getString(cursor.getColumnIndex("full_name"));
                ticket.RefNo = cursor.getLong(cursor.getColumnIndex("refNo"));
                int showId = cursor.getInt(cursor.getColumnIndex("show_id"));

                ticket.showModel = getShow(context, showId, db);

                Cursor ticketTypeCursor = db.rawQuery("SELECT * FROM Seat WHERE ticket_id=?", new String[]{String.valueOf(ticket.id)});
                if (ticketTypeCursor.moveToFirst()) {
                    do {
                        SeatModel seat = new SeatModel();
                        seat.id = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("id"));
                        seat.seatType = ticketTypeCursor.getString(ticketTypeCursor.getColumnIndex("seatType"));
                        seat.seatCount = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("seatCount"));
                        seat.price = ticketTypeCursor.getInt(ticketTypeCursor.getColumnIndex("price"));

                        ticket.seatB.add(seat);
                    } while (ticketTypeCursor.moveToNext());
                }
                ticketTypeCursor.close();
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ticket;
    }

    public void insertShowData(ShowModel showModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("showTitle", showModel.showTitle);
        values.put("dayTime", showModel.dayTime);
        values.put("img", showModel.img);
        values.put("date", showModel.date);
        values.put("address", showModel.address);
        values.put("confirmation", showModel.confirmation);
        values.put("accRestriction", TextUtils.join(",", showModel.accRestriction));
        long newRowId = db.insert("Show", null, values);
        for (TicketTypeModel ticketType : showModel.ticketTypes) {
            ContentValues ticketValues = new ContentValues();
            ticketValues.put("show_id", newRowId);
            ticketValues.put("type", ticketType.type);
            ticketValues.put("count", ticketType.count);
            ticketValues.put("price", ticketType.price);
            db.insertOrThrow("TicketType", null, ticketValues);
        }
        db.close();
    }
    public void insertUser(UserModel user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.email);
        values.put("username", user.username);
        values.put("password", user.password);
         db.insert("User", null, values);
        db.close();
    }

    public void insertTicket(TicketModel ticket){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("show_id", ticket.showModel.id);
        values.put("refNo", System.currentTimeMillis());
        values.put("note", ticket.note);
        values.put("user_id", ticket.userId);
        values.put("full_name", ticket.fullName);
        long newRowId = db.insert("Ticket", null, values);
        for (int i = 0; i<ticket.seatB.size(); i++){
            SeatModel seat = ticket.seatB.get(i);
            if (seat.seatCount > 0) {
                setSeatCount(ticket.showModel.ticketTypes.get(i).id, (ticket.showModel.ticketTypes.get(i).count - seat.seatCount), db);
                ContentValues seatValues = new ContentValues();
                seatValues.put("seatType", seat.seatType);
                seatValues.put("seatCount", seat.seatCount);
                seatValues.put("price", seat.price);
                seatValues.put("ticket_id", newRowId);
                db.insert("Seat", null, seatValues);
            }
        }
        db.close();
    }

    public void setSeatCount(int seatId, int count, SQLiteDatabase db){
        db.execSQL("UPDATE TicketType SET count = ? WHERE id = ?", new String[]{String.valueOf(count), String.valueOf(seatId)});
    }

    public void deleteTicket(TicketModel ticket, Context context){
        SQLiteDatabase db = getWritableDatabase();
        for (TicketTypeModel st : ticket.showModel.ticketTypes){
            for (SeatModel s : ticket.seatB){
                if (s.seatType.equals(st.type)){
                    setSeatCount(st.id, (st.count + s.seatCount), db );
                }
            }
        }
        db.delete("Ticket","id = ?", new String[]{String.valueOf(ticket.id)});
        db.delete("Seat","ticket_id = ?", new String[]{String.valueOf(ticket.id)});
        db.close();
    }
}

