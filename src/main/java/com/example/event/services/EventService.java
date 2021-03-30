package com.example.event.services;

import java.util.ArrayList;
import java.util.List;

import com.example.event.dto.EventDTO;
import com.example.event.entities.Event;
import com.example.event.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    
    @Autowired
    private EventRepository repo;

    public List<com.example.event.dto.EventDTO> getEvents() {       
        List<Event> list = repo.findAll();
        return toDTOList(list);
    }


    private List<EventDTO> toDTOList(List<Event> list) {
        List<EventDTO> listDTO = new ArrayList<>();

        for (Event e : list) {
            listDTO.add(new EventDTO(e.getName(), e.getDescription(), e.getPlace(), e.getEmailContact()));
        }
        return listDTO;
    }


}
