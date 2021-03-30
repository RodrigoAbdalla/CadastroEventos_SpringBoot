package com.example.event.repositories;

import com.example.event.entities.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository <Event,Long> {
    @Query("SELECT c FROM Event c ") 
    public Page<Event> find(Pageable pageRequest);
    
}
