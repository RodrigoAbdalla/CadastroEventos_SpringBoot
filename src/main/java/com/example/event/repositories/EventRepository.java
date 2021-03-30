package com.example.event.repositories;

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
           "LOWER(e.place)  LIKE   LOWER(CONCAT('%', :place, '%'))  AND " +
           "LOWER(e.description)  LIKE   LOWER(CONCAT('%', :description, '%'))      "
    )
    public Page<Event> find(Pageable pageRequest, String name, String place, String description);
    
}
