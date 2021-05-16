package com.example.event.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.AttendeeDTO;
import com.example.event.dto.AttendeeInsertDTO;
import com.example.event.dto.AttendeeUpdateDTO;
import com.example.event.entities.Attendee;
import com.example.event.repositories.AttendeeRepository;

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

    public Page<AttendeeDTO> getAttendees(PageRequest pageRequest, String name, String email, Double balance) {


        try{                                                
            Page<Attendee> list = repo.find(pageRequest, name, email, balance);     
            return list.map( e -> new AttendeeDTO(e));
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error trying to Convert to DataType. Please note that Data Format is yyyy/mm/dd");
        }
  
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
            insertDTO.getEmail()        == null  || 
            insertDTO.getBalance()      == null
            
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        
        Attendee entity = new Attendee(insertDTO);
        entity = repo.save(entity);
        return new AttendeeDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }

    public AttendeeDTO update(Long id, AttendeeUpdateDTO updateDTO) {
        try {
            Attendee entity = repo.getOne(id);
            entity.setName(updateDTO.getName());
            entity.setEmail(updateDTO.getEmail());
            entity.setBalance(updateDTO.getBalance());

            entity = repo.save(entity);
            return new AttendeeDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }

}
