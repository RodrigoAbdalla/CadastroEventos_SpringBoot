package com.example.event.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.event.dto.TicketInsertDeletelDTO;

@Entity
@Table(name="TBL_TICKETS")
public class Ticket implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TicketType type;
    private Instant date;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "ID_EVENT") 
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ID_ATTENDEE") 
    private Attendee attendee;

    public Ticket() {
    }

    
    public Ticket(TicketInsertDeletelDTO insertTicket, Attendee attendee, Event event) {
        this.attendee = attendee;
        this.date = Instant.now();
        this.event = event;
        // Validacao para caso o ticket seja de graca, o valor dele sera zero.
        if(insertTicket.getType().toUpperCase().compareTo("FREE") == 0){
            this.type = TicketType.FREE;
            this.price =  0.0;
        }
        else if(insertTicket.getType().toUpperCase().compareTo("PAYED") == 0){
            this.type = TicketType.PAYED;
            this.price =  event.getPriceTicket();
        }
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ticket other = (Ticket) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Ticket [attendee=" + attendee + ", date=" + date + ", event=" + event + ", id=" + id + ", price="
                + price + ", type=" + type + "]";
    }
    
}
