package com.example.event.services;

import java.util.ArrayList;
import java.util.List;
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

    public Page<EventDTO> getEvents(PageRequest pageRequest) {
        
        Page<Event> list = repo.find(pageRequest);

        return list.map( e -> new EventDTO(e));
    }


    public EventDTO getEventById(Long id) {
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return new EventDTO(event);
    }

    public EventDTO insert(EventInsertDTO insertDTO) {
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
