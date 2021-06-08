package com.example.event.dto;

import com.example.event.entities.Attendee;
import com.example.event.entities.TicketType;

public class TicketInsertDeletelDTO {
    private TicketType type;
    private Attendee attendee;

    
    public TicketType getType() {
        return type;
    }
    public void setType(TicketType type) {
        this.type = type;
    }
    public Instant getDate() {
        return date;
    }
    public void setDate(Instant date) {
        this.date = date;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }


    public Event getEvent() {
        return event;
    }


    public void setEvent(Event event) {
        this.event = event;
    }
    

    public Attendee getAttendee() {
        return attendee;
    }


    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }
}
