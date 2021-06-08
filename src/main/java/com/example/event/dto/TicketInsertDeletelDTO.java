package com.example.event.dto;

import com.example.event.entities.Ticket;

public class TicketInsertDeletelDTO {
    private String type;
    private Long idAttendee;

    public TicketInsertDeletelDTO(String type, Long idAttendee) {
        setAttendee(idAttendee);
        setType(type);
	}

    public TicketInsertDeletelDTO(Ticket ticket) {
        setAttendee(ticket.getAttendee().getId());
        setType(ticket.getType().toString());
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getAttendee() {
        return idAttendee;
    }

    public void setAttendee(Long idAttendee) {
        this.idAttendee = idAttendee;
    }
}
