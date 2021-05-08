package com.example.event.repositories;

import com.example.event.entities.BaseUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository extends JpaRepository <BaseUser,Long> {
    @Query("SELECT e FROM BaseUser e " + 
           "WHERE " +
           "LOWER(e.name)     LIKE   LOWER(CONCAT('%', :name, '%')) AND " +
           "LOWER(e.email)  LIKE   LOWER(CONCAT('%', :email, '%'))"
    )
    public Page<BaseUser> find(Pageable pageRequest, String name, String email);
    
    
}
