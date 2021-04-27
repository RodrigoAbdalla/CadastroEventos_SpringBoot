package com.example.event.repositories;

import com.example.event.entities.Place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository <Place,Long> {
    @Query("SELECT e FROM Place e " + 
           "WHERE " +
           "LOWER(e.name)     LIKE   LOWER(CONCAT('%', :name, '%')) AND " +
           "LOWER(e.adress)  LIKE   LOWER(CONCAT('%', :adress, '%'))"
    )
    public Page<Place> find(Pageable pageRequest, String name, String adress);
    
}
