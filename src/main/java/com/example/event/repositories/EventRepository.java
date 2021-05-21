package com.example.event.repositories;

import java.time.LocalDate;
import java.util.List;

import com.example.event.entities.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository <Event,Long> {
    @Query("SELECT e FROM Event e " +
           "WHERE " +
           "LOWER(e.name)     LIKE   LOWER(CONCAT('%', :name, '%')) AND " +
           "LOWER(e.description)  LIKE   LOWER(CONCAT('%', :description, '%'))   AND   "    +
           "e.startDate  > :startDate AND "   +                 // Filtro para receber os eventos com a data maior do que a solicitada
           "e.priceTicket <= :priceTicket "                      // Filtro para receber os eventos com o preço do ticket menor ou igual ao solicitado
    )
    public Page<Event> find(Pageable pageRequest, String name, String description, LocalDate startDate, Double  priceTicket);
    
    @Query("SELECT e FROM Event e " +
    "WHERE " +                    // Filtro para receber os Eventos que possum o admin solicitado 
    "e.admin.id = :admin"
    )
    public List<Event> findByAdmin(Long admin);

    
}
