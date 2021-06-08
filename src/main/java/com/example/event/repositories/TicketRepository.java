package com.example.event.repositories;

import java.util.List;

import com.example.event.entities.Ticket;
import com.example.event.entities.TicketType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository <Ticket,Long> {
    @Query("SELECT t FROM Ticket t " +
    "WHERE " +                    // Filtro para receber os Tickets que possum o attendee solicitado
    "t.attendee.id = :attendee"
    )
    public List<Ticket> findByAttendee(Long attendee);

    @Query("SELECT t FROM Ticket t " +
    "WHERE " +                    // Filtro para receber os Tickets que possum o evento solicitado
    "t.event.id = :event"
    )
    public List<Ticket> findByEvent(Long event);


    @Query("SELECT t FROM Ticket t " +
    "WHERE " +                    // Filtro para receber os Tickets que possum o evento solicitado
    "t.type = 0 AND " + 
    "t.event.id = :event"
    )
    public List<Ticket> findFreeTicketsSold(Long event);


    @Query("SELECT t FROM Ticket t " +
    "WHERE " +                    // Filtro para receber os Tickets que possum o evento solicitado
    "t.type = 1 AND " + 
    "t.event.id = :event"
    )
    public List<Ticket> findPayedTicketsSold(Long event);


    @Query("SELECT t FROM Ticket t " +
    "WHERE " +                    // Filtro para receber os Tickets que possum o evento solicitado
    "t.type = :type AND " + 
    "t.event.id = :event AND " + 
    "t.attendee.id = :attendee"
    )
    public List<Ticket> findTickets(Long event, Long attendee, TicketType type);


}
