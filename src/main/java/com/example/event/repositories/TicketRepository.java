package com.example.event.repositories;

import java.util.List;

import com.example.event.entities.Ticket;
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

}
