package com.example.event.repositories;

import com.example.event.entities.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository extends JpaRepository <Admin,Long> {
    
}
