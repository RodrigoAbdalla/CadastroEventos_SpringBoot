package com.example.event.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.EventDTO;
import com.example.event.dto.EventInsertDTO;
import com.example.event.dto.EventUpdateDTO;
import com.example.event.entities.Event;
import com.example.event.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private EventRepository repo;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String place, String description, String  startDateString) {



                                                                             // FORMATO DE DATA ACEITO = yyyy-mm-dd  || yyyy/mm/dd  || yyyy.mm.dd 
        if(startDateString.contains("/")){                                  // Logica para trocar os caracteres incorretos, caso nao seja "-"
            startDateString = startDateString.replace("/", "-");
        }
        else if(startDateString.contains(".")){
            startDateString = startDateString.replace(".", "-");
        }
        try{                                                                        // Mapeando o erro para caso o usuario tente colocar uma data nom formato errado
            LocalDate startDate = LocalDate.parse(startDateString.trim());          // TRANSFORMA A STRING RECEBIDA EM UMA VARIAVEL LOCAL DATE 
            Page<Event> list = repo.find(pageRequest, name, place, description, startDate);     
            return list.map( e -> new EventDTO(e));
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error trying to Convert to DataType. Please note that Data Format is yyyy/mm/dd");
        }
         
        

        
    }


    public EventDTO getEventById(Long id) {
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return new EventDTO(event);
    }

    public EventDTO insert(EventInsertDTO insertDTO) {
        if( 
            insertDTO.getName()         == ""    ||                 // Logica para o programa nao aceitar nomes, descrições e nem lugares vazios / nulos
            insertDTO.getDescription()  == ""    || 
            insertDTO.getPlace()        == ""    || 
            insertDTO.getName()         == null  || 
            insertDTO.getDescription()  == null  || 
            insertDTO.getPlace()        == null  ||
            insertDTO.getStartDate()    == null  ||
            insertDTO.getEndDate()      == null  ||
            insertDTO.getStartTime()    == null  ||
            insertDTO.getEndTime()      == null 
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        else if(
            insertDTO.getStartDate().isAfter(insertDTO.getEndDate()) ||         // Logica para nao aceitar que a data final seja maior que a data inicial
                ((insertDTO.getStartDate().isEqual(insertDTO.getEndDate())) 
                && 
                (insertDTO.getStartTime().isAfter(insertDTO.getEndTime())))
            ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "An event cannot start after the end");
        }
        Event entity = new Event(insertDTO);
        entity = repo.save(entity);
        return new EventDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public EventDTO update(Long id, EventUpdateDTO updateDTO) {
        try {
            Event entity = repo.getOne(id);
            entity.setName(updateDTO.getName());
            entity.setDescription(updateDTO.getDescription());
            entity.setPlace(updateDTO.getPlace());
            entity.setStartDate(updateDTO.getStartDate());
            entity.setEndDate(updateDTO.getEndDate());
            entity.setStartTime(updateDTO.getStartTime());
            entity.setEndTime(updateDTO.getEndTime());
            entity = repo.save(entity);
            return new EventDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

}
