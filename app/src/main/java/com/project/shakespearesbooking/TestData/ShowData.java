package com.project.shakespearesbooking.TestData;

import com.project.shakespearesbooking.models.ShowModel;
import com.project.shakespearesbooking.models.TicketTypeModel;

import java.util.ArrayList;

public class ShowData {
    public ArrayList<ShowModel> shows = new ArrayList<ShowModel>();

    public void addData(){
        ShowModel d1 = new ShowModel();
        d1.id = 1;
        d1.showTitle = "The Merchant of Venice by William Shakespeare";
        d1.dayTime = "20:00 Sunday";
        d1.date = "April 23 2023";
        d1.img = "https://cdn-academyblog.pressidium.com/wp-content/uploads/2020/05/Mother-Courage-and-Her-Children-Wright-State-Theatre-.jpg";
        d1.address = "Merchant Adventurer's Hall, York";
        d1.accRestriction.add("Has wheelchair access");
        TicketTypeModel d1t1 = new TicketTypeModel();
        d1t1.type = "Seated";
        d1t1.count = 17;
        d1t1.price = 8;
        d1.ticketTypes.add(d1t1);
        shows.add(d1);

        ShowModel d2 = new ShowModel();
        d2.id = 2;
        d2.showTitle = "Hamlet by William Shakespeare";
        d2.dayTime = "8:00";
        d2.date = "19 May 2023";
        d2.img = "https://res.cloudinary.com/bloomsbury-publishing-public/image/upload/q_auto,f_auto/dramaonlin/CMS/Charles_Surface_Adam_Redmore_and_the_Company.jpg";
        d2.address = "Clifford's Tower, York";
        d2.confirmation = "Users are required to tick a box to consent to being splattered with fake blood during the performance";
        d2.accRestriction.add("Must be able to climb 20 steps");
        d2.accRestriction.add("Performance includes flashing lights");
        TicketTypeModel d2t1 = new TicketTypeModel();
        d2t1.type = "Seated";
        d2t1.count = 8;
        d2t1.price = 8;
        d2.ticketTypes.add(d2t1);
        TicketTypeModel d2t2 = new TicketTypeModel();
        d2t2.type = "Standing";
        d2t2.count = 12;
        d2t2.price = 7;
        d2.ticketTypes.add(d2t2);
        shows.add(d2);

        ShowModel d3 = new ShowModel();
        d3.id = 3;
        d3.showTitle = "A Midsummer Night's Dream by William Shakespeare";
        d3.dayTime = "19:00 Saturday";
        d3.date = "3 June 2023";
        d3.img = "https://images.squarespace-cdn.com/content/v1/5bc800cb9d41495b62cda8df/1570684303444-8TEJ89J42BVXDKCZTQPE/barber-895147_960_720.jpg";
        d3.address = "Dean's Park, York";
        d3.accRestriction.add("Has wheelchair access");
        d3.accRestriction.add("Performance includes flashing lights");
        TicketTypeModel d3t1 = new TicketTypeModel();
        d3t1.type = "On Stage";
        d3t1.count = 11;
        d3t1.price = 4;
        d3.ticketTypes.add(d3t1);
        TicketTypeModel d3t2 = new TicketTypeModel();
        d3t2.type = "Grass";
        d3t2.count = 29;
        d3t2.price = 4;
        d3.ticketTypes.add(d3t2);
        shows.add(d3);

        ShowModel d4 = new ShowModel();
        d4.id = 4;
        d4.showTitle = "Oedipus the King by Sophocles";
        d4.dayTime = "20:00 Friday";
        d4.date = "28 July 2023";
        d4.img = "https://i.guim.co.uk/img/media/c308350857416b8537c0a3b5442cffa3ca03ed31/0_0_2000_1334/master/2000.jpg?width=700&quality=85&auto=format&fit=max&s=c2a41deeee459db5c05f85ad07d1e2a0";
        d4.address = "Mary's Abbey, Museum Gardens, York";
        d4.accRestriction.add("Has wheelchair access");
        TicketTypeModel d4t1 = new TicketTypeModel();
        d4t1.type = "Seated";
        d4t1.count = 5;
        d4t1.price = 9;
        d4.ticketTypes.add(d4t1);
        TicketTypeModel d4t2 = new TicketTypeModel();
        d4t2.type = "Standing";
        d4t2.count = 12;
        d4t2.price = 7;
        d4.ticketTypes.add(d4t2);
        shows.add(d4);

        ShowModel d5 = new ShowModel();
        d5.id = 5;
        d5.showTitle = "The Tempest by William Shakespeare";
        d5.dayTime = "14:00 Saturday";
        d5.date = "19 August 2023";
        d5.img = "https://media.timeout.com/images/102074921/750/422/image.jpg";
        d5.address = "Milleneum Bridge, York";
        d5.accRestriction.add("Has wheelchair access");
        TicketTypeModel d5t1 = new TicketTypeModel();
        d5t1.type = "Boat A";
        d5t1.count = 6;
        d5t1.price = 9;
        d5.ticketTypes.add(d5t1);
        TicketTypeModel d5t2 = new TicketTypeModel();
        d5t2.type = "Boat B";
        d5t2.count = 4;
        d5t2.price = 9;
        d5.ticketTypes.add(d5t2);
        TicketTypeModel d5t3 = new TicketTypeModel();
        d5t3.type = "Riverbank";
        d5t3.count = 10;
        d5t3.price = 7;
        d5.ticketTypes.add(d5t3);
        shows.add(d5);

        ShowModel d6 = new ShowModel();
        d6.id = 6;
        d6.showTitle = "Antigone by Sophocles";
        d6.dayTime = "21:00 Thursday";
        d6.date = "20 September 2023";
        d6.img = "https://cdn-academyblog.pressidium.com/wp-content/uploads/2020/05/life-is-a-dream-Sadlers-Wells-Independent-1024x768.jpg";
        d6.address = "Crypt, York Minster, York";
        d6.accRestriction.add("Must be able to climb 20 steps");
        TicketTypeModel d6t1 = new TicketTypeModel();
        d6t1.type = "Inner circle";
        d6t1.count = 5;
        d6t1.price = 16;
        d6.ticketTypes.add(d6t1);
        TicketTypeModel d6t2 = new TicketTypeModel();
        d6t2.type = "Outer circle";
        d6t2.count = 8;
        d6t2.price = 13;
        d6.ticketTypes.add(d6t2);
        TicketTypeModel d6t3 = new TicketTypeModel();
        d6t3.type = "Standing";
        d6t3.count = 10;
        d6t3.price = 10;
        d6.ticketTypes.add(d6t3);
        shows.add(d6);


    }
}
