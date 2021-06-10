package com.example.event.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.AttendeeDTO;
import com.example.event.dto.AttendeeInsertDTO;
import com.example.event.dto.AttendeeUpdateDTO;
import com.example.event.entities.Admin;
import com.example.event.entities.Attendee;
import com.example.event.entities.Ticket;
import com.example.event.repositories.AdminRepository;
import com.example.event.repositories.AttendeeRepository;
import com.example.event.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository repo;

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private TicketRepository ticketRepository;

    public Page<AttendeeDTO> getAttendees(PageRequest pageRequest, String name, String email, Double balance) {

        Page<Attendee> list = repo.find(pageRequest, name, email, balance);     
        return list.map( e -> new AttendeeDTO(e));
  
    }


    public AttendeeDTO getAttendeeById(Long id) {
        Optional<Attendee> op = repo.findById(id);
        Attendee attendee = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found"));
        return new AttendeeDTO(attendee);
    }

    public AttendeeDTO insert(AttendeeInsertDTO insertDTO) {
        if( 
            insertDTO.getName()         == ""    ||                 // Logica para o programa nao aceitar nomes, descrições e nem lugares vazios / nulos
            insertDTO.getEmail()        == ""    || 
            insertDTO.getName()         == null  || 
            insertDTO.getEmail()        == null
            
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        else if(!insertDTO.getEmail().contains("@") || !insertDTO.getEmail().contains(".com")){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill a valid email.");
        }
        // Verificação se o email já esta sendo usado, tanto pelos attendees quanto pelos admins
        List<Admin> admins = adminRepository.findAll();
        for (Admin admin : admins) {
            if(admin.getEmail().compareTo(insertDTO.getEmail()) == 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
            }
        }
        List<Attendee> attendees = repo.findAll();
        for (Attendee attendee : attendees) {
            if(attendee.getEmail().compareTo(insertDTO.getEmail()) == 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
            }
        }
        
        Attendee entity = new Attendee(insertDTO);
        entity = repo.save(entity);
        return new AttendeeDTO(entity);
    }

    public void delete(Long id) {
        try {
            List<Ticket> ticket = new ArrayList<>();
            // Procura se possui um ticket com o attendee cadastrado. Caso possua, não será possível realizar o delete
            // Sendo assim necessário vender o ticket primeiro, com o DELETE /events/{id}/tickets, disponível apenas nas AF.
            ticket = ticketRepository.findByAttendee(id);
            if(ticket.size() != 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This Attendee has a ticket. To remove an attendee, you need to sell all their tickets first");
            }
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }

    public AttendeeDTO update(Long id, AttendeeUpdateDTO updateDTO) {
        try {

            if(!updateDTO.getEmail().contains("@") || !updateDTO.getEmail().contains(".com")){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill a valid email.");
            }
            // Verificação se o email já esta sendo usado, tanto pelos attendees quanto pelos admins
            List<Admin> admins = adminRepository.findAll();
            for (Admin admin : admins) {
                if(admin.getEmail().compareTo(updateDTO.getEmail()) == 0){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
                }
            }
            List<Attendee> attendees = repo.findAll();
            for (Attendee attendee : attendees) {
                if(attendee.getEmail().compareTo(updateDTO.getEmail()) == 0){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
                }
            }

            Attendee entity = repo.getOne(id);
            entity.setName(updateDTO.getName());
            entity.setEmail(updateDTO.getEmail());

            entity = repo.save(entity);
            return new AttendeeDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }

}
