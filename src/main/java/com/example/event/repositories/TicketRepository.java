package com.example.event.repositories;

import java.time.Instant;

import com.example.event.entities.Ticket;
import com.example.event.entities.TicketType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository <Ticket,Long> {
    @Query("SELECT e FROM Ticket e " + 
           "WHERE " +
           "e.type = LOWER('FREE') || e.type = LOWER('PAYED') AND " +           //JOÃO: Não sei se a consulta do type está certa
           "e.date  > :date AND " +
           "e.price  >=   :price"
    ) //JOÃO: Não entendi o erro dessa linha

    //public Page<Ticket> find(Pageable pageRequest, TicketType type, Instant date, Double price);          JOÃO: É NECESSÁRIO????
    
}
