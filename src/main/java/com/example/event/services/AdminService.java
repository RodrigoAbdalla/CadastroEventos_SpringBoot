package com.example.event.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.AdminDTO;
import com.example.event.dto.AdminInsertDTO;
import com.example.event.dto.AdminUpdateDTO;
import com.example.event.entities.Admin;
import com.example.event.entities.Attendee;
import com.example.event.entities.Event;
import com.example.event.repositories.AdminRepository;
import com.example.event.repositories.AttendeeRepository;
import com.example.event.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private EventRepository eventRepository;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest, String name, String email, String phoneNumber) {

        Page<Admin> list = repo.find(pageRequest, name, email, phoneNumber);     
        return list.map( e -> new AdminDTO(e));  
    }


    public AdminDTO getAdminById(Long id) {
        Optional<Admin> op = repo.findById(id);
        Admin admin = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
        return new AdminDTO(admin);
    }

    public AdminDTO insert(AdminInsertDTO insertDTO) {
        
        // Logica para o programa nao aceitar nomes, descrições e nem lugares vazios / nulos
        if( 
            insertDTO.getName()         == ""    ||                 
            insertDTO.getEmail()        == ""    || 
            insertDTO.getPhoneNumber()  == ""    || 
            insertDTO.getName()         == null  || 
            insertDTO.getEmail()        == null  || 
            insertDTO.getPhoneNumber()  == null
            
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }

        if(!insertDTO.getEmail().contains("@") || !insertDTO.getEmail().contains(".com")){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill a valid email.");
        }

        // Verificação se o email já esta sendo usado, tanto pelos attendees quanto pelos admins
        List<Admin> admins = repo.findAll();
        for (Admin admin : admins) {
            if(admin.getEmail().compareTo(insertDTO.getEmail()) == 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
            }
        }
        List<Attendee> attendees = attendeeRepository.findAll();
        for (Attendee attendee : attendees) {
            if(attendee.getEmail().compareTo(insertDTO.getEmail()) == 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
            }
        }
        
        Admin entity = new Admin(insertDTO);
        entity = repo.save(entity);
        return new AdminDTO(entity);
    }

    public void delete(Long id) {
        try {
            List<Event> event = new ArrayList<>();

            //Procura se possui um evento com o admin cadastrado. Caso possua, não será possível realizar o delete, sendo necessário excluir o evento primeiro
            event = eventRepository.findByAdmin(id);
            if(event.size() != 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This Admin has an Event. To remove an admin, you need to first delete the associated event.");
            }
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public AdminDTO update(Long id, AdminUpdateDTO updateDTO) {
        try {

            if(!updateDTO.getEmail().contains("@") || !updateDTO.getEmail().contains(".com")){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill a valid email.");
            }
            
            // Verificação se o email já esta sendo usado, tanto pelos attendees quanto pelos admins
            List<Admin> admins = repo.findAll();
            for (Admin admin : admins) {
                if(admin.getEmail().compareTo(updateDTO.getEmail()) == 0){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
                }
            }
            List<Attendee> attendees = attendeeRepository.findAll();
            for (Attendee attendee : attendees) {
                if(attendee.getEmail().compareTo(updateDTO.getEmail()) == 0){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This emails is already in use. Please choose another one.");
                }
            }



            Admin entity = repo.getOne(id);
            entity.setName(updateDTO.getName());
            entity.setEmail(updateDTO.getEmail());
            entity.setPhoneNumber(updateDTO.getPhoneNumber());

            entity = repo.save(entity);
            return new AdminDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

}
