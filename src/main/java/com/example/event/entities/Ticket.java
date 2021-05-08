package com.example.event.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="TBL_TICKETS") //Vai ter tabela para cada entidade???
public class Ticket implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TicketType type;
    private Instant date;
    private Double price;

    /*@ManyToMany(mappedBy = "tickets")
    private List <Event> events = new ArrayList<>();*/         //JOÃO: Não sei se vai precisar disso também

    public Ticket() {
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

    /*public List<Event> getEvents() {
        return events;
    }
                                                //JOÃO: NÃO SEI SE VAI PRECISAR DE UMA LISTA
    public void addEvent(Event event) {
        this.events.add(event);
    }*/

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
}
