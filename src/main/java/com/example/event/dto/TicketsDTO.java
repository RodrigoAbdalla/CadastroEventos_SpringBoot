package com.example.event.dto;

import com.example.event.entities.Attendee;
import com.example.event.entities.Ticket;
import com.example.event.entities.TicketType;

public class TicketsDTO {
    private TicketType type;
    private AttendeeDTO attendee;

    public TicketsDTO(TicketType type, Attendee attendee) {
        setAttendee(new AttendeeDTO(attendee));
        setType(type);
	}

    public TicketsDTO(Ticket ticket) {
        setAttendee(new AttendeeDTO(ticket.getAttendee()));
        setType(ticket.getType());
    }

    public TicketType getType() {
        return type;
    }
    public void setType(TicketType type) {
        this.type = type;
    }

    public AttendeeDTO getAttendee() {
        return attendee;
    }

    public void setAttendee(AttendeeDTO attendee) {
        this.attendee = attendee;
    }
}
