package com.example.event.repositories;

import com.example.event.entities.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository <Admin,Long> {
    @Query("SELECT e FROM Admin e " + 
           "WHERE " +
           "LOWER(e.phoneNumber)  LIKE   LOWER(CONCAT('%', :phoneNumber, '%')) "
    )
    public Page<Admin> find(Pageable pageRequest, String phoneNumber);
    
    
}
